package com.example.gamePT.global.handler;

import com.example.gamePT.domain.chatLog.entity.ChatLog;
import com.example.gamePT.domain.chatLog.service.ChatLogService;
import com.example.gamePT.domain.chatthingRoom.entity.ChattingRoom;
import com.example.gamePT.domain.chatthingRoom.serivce.ChattingRoomService;
import com.example.gamePT.domain.user.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import groovy.util.logging.Slf4j;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class SocketHandler extends TextWebSocketHandler {
    List<HashMap<String, Object>> rls = new ArrayList<>(); //웹소켓 세션을 담아둘 리스트 ---roomListSessions

    private final ChattingRoomService chattingRoomService;
    private final ChatLogService chatLogService;
    private final UserService userService;
    private final Gson gson;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {

        //메시지 발송
        String msg = message.getPayload(); //JSON형태의 String메시지를 받는다.

        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> obj = gson.fromJson(msg, type);

        ChattingRoom cr = chattingRoomService.findById(Long.parseLong((String)obj.get("roomNumber")));

        ChatLog chatLog = ChatLog.builder()
                .chattingRoom(cr)
                .content((String) obj.get("content"))
                .isCheck(false)
                .sender(userService.findByUsername((String) obj.get("username")))
                .build();

        String rN = (String) obj.get("roomNumber"); //방의 번호를 받는다.
        HashMap<String, Object> temp = new HashMap<String, Object>();
        if(rls.size() > 0) {
            for(int i=0; i<rls.size(); i++) {
                String roomNumber = (String) rls.get(i).get("roomNumber"); //세션리스트의 저장된 방번호를 가져와서
                if(roomNumber.equals(rN)) { //같은값의 방이 존재한다면
                    temp = rls.get(i); //해당 방번호의 세션리스트의 존재하는 모든 object값을 가져온다.
                    break;
                }
            }

            if(temp.size() > 2){
                chatLog.toBuilder().isCheck(true).build();
            }

            chatLogService.save(chatLog);

            //해당 방의 세션들만 찾아서 메시지를 발송해준다.
            for(String k : temp.keySet()) {
                if(k.equals("roomNumber")) { //다만 방번호일 경우에는 건너뛴다.
                    continue;
                }

                WebSocketSession wss = (WebSocketSession) temp.get(k);
                if(wss != null) {
                    try {
                        if(obj.get("isDelete").equals("true")){
                            rls.remove(temp);
                            chattingRoomService.delete(cr);
                        }

                        TextMessage textMessage = new TextMessage(gson.toJson(obj));
                        wss.sendMessage(textMessage);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //소켓 연결
        super.afterConnectionEstablished(session);
        boolean flag = false;
        String url = session.getUri().toString();
        String roomNumber = url.split("/chat/")[1];
        int idx = rls.size(); //방의 사이즈를 조사한다.
        if(rls.size() > 0) {
            for(int i=0; i<rls.size(); i++) {
                String rN = (String) rls.get(i).get("roomNumber");
                if(rN.equals(roomNumber)) {
                    flag = true;
                    idx = i;
                    break;
                }
            }
        }

        if(flag) { //존재하는 방이라면 세션만 추가한다.
            HashMap<String, Object> map = rls.get(idx);
            map.put(session.getId(), session);
        }else { //최초 생성하는 방이라면 방번호와 세션을 추가한다.
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("roomNumber", roomNumber);
            map.put(session.getId(), session);
            rls.add(map);
        }

        //세션등록이 끝나면 발급받은 세션ID값의 메시지를 발송한다.
        JSONObject obj = new JSONObject();
        obj.put("type", "getId");
        obj.put("sessionId", session.getId());
        obj.put("isDelete", false);
        session.sendMessage(new TextMessage(obj.toJSONString()));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        //소켓 종료
        if(rls.size() > 0) { //소켓이 종료되면 해당 세션값들을 찾아서 지운다.
            for(int i=0; i<rls.size(); i++) {
                rls.get(i).remove(session.getId());
            }
        }
        super.afterConnectionClosed(session, status);
    }
}
