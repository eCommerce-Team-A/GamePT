package com.example.gamePT.domain.chatthingRoom.controller;

import com.example.gamePT.domain.chatthingRoom.entity.ChattingRoom;
import com.example.gamePT.domain.chatthingRoom.serivce.ChattingRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chattingRoom")
public class ChattingRoomController {

    private final ChattingRoomService chattingRoomService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String chattingRooms (Model model){

        List<ChattingRoom> chattingRooms = chattingRoomService.getChattingRoomsByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        model.addAttribute("chattingRooms",chattingRooms);

        return "/chattingRoom/list";
    }

}
