package com.example.gamePT.domain.chatthingRoom.serivce;

import com.example.gamePT.domain.chatthingRoom.entity.ChattingRoom;
import com.example.gamePT.domain.chatthingRoom.repoisotry.ChattingRoomRepository;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public ChattingRoom create(SiteUser siteUser, SiteUser expert){
        ChattingRoom cr = ChattingRoom.builder().siteUser(siteUser).expert(expert).build();
        return chattingRoomRepository.save(cr);
    }

    public ChattingRoom getChattingRoomsByBuyUserAndExpert(SiteUser siteUser, SiteUser expert ){

        Optional<ChattingRoom> cr = chattingRoomRepository.findBySiteUserAndExpert(siteUser,expert);

        if(cr.isEmpty()){
            return null;
        }

        return cr.get();
    }

    public ChattingRoom getChattingRoomById(Long id) {
        return chattingRoomRepository.findById(id).get();
    }
}
