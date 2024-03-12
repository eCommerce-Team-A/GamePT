package com.example.gamePT.domain.chatthingRoom.controller;

import com.example.gamePT.domain.chatthingRoom.entity.ChattingRoom;
import com.example.gamePT.domain.chatthingRoom.serivce.ChattingRoomService;
import com.example.gamePT.domain.image.service.ImageService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chattingRoom")
public class ChattingRoomController {

    private final UserService userService;
    private final ChattingRoomService chattingRoomService;
    private final ImageService imageService;
    private final Gson gson;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String chattingRooms (Model model){

        List<ChattingRoom> chattingRooms = chattingRoomService.getChattingRoomsByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("chattingRooms",chattingRooms);

        return "/chattingRoom/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/getChattingRoomById/{id}")
    @ResponseBody
    public ChattingRoomResponse getChattingRoomById (@PathVariable(value = "id") Long id){

        ChattingRoom chattingRoom = chattingRoomService.getChattingRoomById(id);

        String siteUserImgPath = imageService.findByUser(chattingRoom.getSiteUser()).getPath();
        String expertImgPath = imageService.findByUser(chattingRoom.getExpert()).getPath();

        return new ChattingRoomResponse(chattingRoom, siteUserImgPath, expertImgPath);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create/{siteUserId}")
    public String createChattingRoom (@PathVariable(value = "siteUserId") Long expertUserId){

        SiteUser expertUser = userService.findByUserId(expertUserId);
        SiteUser logindUser = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if(expertUser.getId() == logindUser.getId()){
            return "history_back";
        }
        ChattingRoom cr = chattingRoomService.getChattingRoomsByBuyUserAndExpert(logindUser,expertUser);

        if(cr == null){
            chattingRoomService.create(logindUser,expertUser);
        }

        return "redirect:/chattingRoom/list";
    }

    @Setter
    @Getter
    public static class ChattingRoomResponse{
        private ChattingRoom chattingRoom;
        private String siteUserImgPath;
        private String expertImgPath;

        ChattingRoomResponse(ChattingRoom chattingRoom, String siteUserImgPath, String expertImgPath){
            this.chattingRoom = chattingRoom;
            this.siteUserImgPath = siteUserImgPath;
            this.expertImgPath = expertImgPath;
        }
    }


}
