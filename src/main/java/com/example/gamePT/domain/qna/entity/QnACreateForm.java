package com.example.gamePT.domain.qna.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnACreateForm {

    private String title;
    private String content;
    // 공개 여부
    private Boolean isBlind;
}
