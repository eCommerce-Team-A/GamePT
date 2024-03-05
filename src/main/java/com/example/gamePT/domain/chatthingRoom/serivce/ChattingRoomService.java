package com.example.gamePT.domain.chatthingRoom.serivce;

import com.example.gamePT.domain.chatthingRoom.entity.ChattingRoom;
import com.example.gamePT.domain.chatthingRoom.repoisotry.ChattingRoomRepository;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChattingRoomService {

    private final ChattingRoomRepository chattingRoomRepository;
    private final UserService userService;

    public ChattingRoom findById(Long id){
        return chattingRoomRepository.findById(id).get();
    }

    public List<ChattingRoom> getChattingRoomsByUsername(String username) {
        SiteUser logindUser = userService.findByUsername(username);
        return chattingRoomRepository.findBySiteUserOrExpert(logindUser,logindUser);
    }
}
