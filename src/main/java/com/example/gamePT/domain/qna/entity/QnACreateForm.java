package com.example.gamePT.domain.qna.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QnACreateForm {

    private Long id;

    @NotEmpty(message = "제목은 필수 입니다.")
    private String title;

    @NotEmpty(message = "내용은 필수 입니다")
    private String content;

    // 공개 여부
    private Boolean isBlind;
}
