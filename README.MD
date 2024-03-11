## GamePT(게임 과외 eCommerce 플랫폼)

### 서비스 설명 및 기획 의도

- E-sports 교육 연계 사업 모델 및 함께 게임 할 유저를 찾는 듀오 신청 서비스
- 스프링 부트를 이용한 eCommerce 제작 및 Riot-api를 이용한 듀오 신청 서비스 개발


[[기능정의서](https://docs.google.com/document/d/12oLpqgt0L2I_BdF_KKW2tpO2SkXREyM7zhsUKMOSxBc/edit)]

---

## 🛠 개발환경

1. 사용언어 : JAVA , MariaDB(MySQL), HTML5 , CSS , JS
2. JDK : Amazon Corretto version 17.0.9
3. 에디터 : IntelliJ IDEA, DBeaver
4. DB종류 : MariaDB(MySQL), Server(local)
5. 프레임워크 : SpringBoot
6. 라이브러리 : JPA, thymeleaf, security, lombok, Java-email, bootstrap, jquery


---

## ☁️ ERD ([링크](https://www.erdcloud.com/d/LqovpTTw8Z6LNTNdw))
![img.png](img.png)


---

## 👀 시연영상



---

## 🔥 트러블 슈팅

## 1.


### 이슈 내역
- 채팅 전송 문제

### 문제점 설명
- 웹 소켓으로 1:1 채팅방 구현 후 하나의 채팅 메세지를 보냈는데, 같은 채팅이 여러번 보내짐


![Image](https://github.com/eCommerce-Team-A/GamePT/assets/37567402/4a65f7ae-52f3-4e0d-b07a-511a27f55510)



## 🚥 해결

- 현재 소켓 핸들러에서 연결된 세션과 해당 웹소켓 연결 정보를 담아두기 위한 리스트를 만들어 두었음.
```java
// 웹소켓 세션을 담아둘 리스트
List<HashMap<String, Object>> rls = new ArrayList<>(); 

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
        session.sendMessage(new TextMessage(obj.toJSONString()));
    }

```

-  소켓이 종료되면 해당 세션값들을 세션 아이디로 통해  리스트에서 지움.

```java
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
```

## 결론 
## FrontEnd에서 다른 채팅방 이동시 소켓 연결을 끊고 다시 연결해야 소켓 연결이 쌓이지 않음

```javascript
$(".list-group-item").click(function(){
    $("#unClick").hide();
    $("#chat").show();
    roomNum = $(this).attr("value");

    var url = "ws://localhost:8010/wss/chat/"+roomNum;

    if(url == websocket.url){
        return;
    }

    $("#chattingArea").empty();

    getChattingRoom(roomNum);

    if(websocket != ""){
        websocket.close();
    }

    websocket = new WebSocket(url);
    websocket.onmessage = onMessage;

});
```

