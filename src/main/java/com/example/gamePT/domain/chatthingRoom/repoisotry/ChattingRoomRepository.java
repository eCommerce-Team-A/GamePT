package com.example.gamePT.domain.chatthingRoom.repoisotry;

import com.example.gamePT.domain.chatthingRoom.entity.ChattingRoom;
import com.example.gamePT.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChattingRoomRepository extends JpaRepository<ChattingRoom, Long> {
    List<ChattingRoom> findBySiteUserOrExpert(SiteUser siteUser,SiteUser expert);

    Optional<ChattingRoom> findBySiteUserAndExpert(SiteUser siteUser, SiteUser expert);
}
