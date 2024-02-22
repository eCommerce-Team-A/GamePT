package com.example.gamePT.domain.duoArticle.enity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuoArticleForm {
    @NotEmpty(message = "라인을 선택하세요.")
    private String myLine;
    @NotEmpty(message = "라인을 선택하세요.")
    private String findLine;
    private Boolean microphoneCheck = false;
    @NotEmpty(message = "내용을 입력하세요.")
    private String content;
}
