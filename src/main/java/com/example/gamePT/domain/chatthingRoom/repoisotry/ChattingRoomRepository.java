package com.example.gamePT.domain.chatthingRoom.repoisotry;

import com.example.gamePT.domain.chatthingRoom.entity.ChattingRoom;
import com.example.gamePT.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
    List<ChattingRoom> findBySiteUserOrExpert(SiteUser siteUser,SiteUser expert);
}
