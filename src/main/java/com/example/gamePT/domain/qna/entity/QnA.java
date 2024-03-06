package com.example.gamePT.domain.qna.entity;

import com.example.gamePT.domain.user.entity.SiteUser;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class QnA {

    @ManyToOne
    private SiteUser writer;

    private String title;
    private String content;

    // 공개 여부
    private Boolean isBlind;

    // 답변 여부
    private Boolean isAnswered;

    private String answer;
    private LocalDateTime answerDate;
}
