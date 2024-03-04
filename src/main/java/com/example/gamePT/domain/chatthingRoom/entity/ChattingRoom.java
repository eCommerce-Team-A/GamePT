package com.example.gamePT.domain.chatthingRoom.entity;

import com.example.gamePT.domain.chatLog.entity.ChatLog;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ChattingRoom extends BaseEntity {

    @ManyToOne
    private SiteUser siteUser;

    @ManyToOne
    private SiteUser expert;

    @OneToMany(mappedBy = "chattingRoom", cascade = CascadeType.REMOVE)
    @OrderBy("createDate asc")
    private List<ChatLog> chatLogs;

}
