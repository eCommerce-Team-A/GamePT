package com.example.gamePT.domain.duoArticle.enity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
public class DuoArticleCreateForm {
    @NotEmpty(message = "라인을 선택하세요.")
    private String myLine;
    @NotEmpty(message = "라인을 선택하세요.")
    private String findLine;
    @NotEmpty(message = "마이크 여부를 선택하세요.")
    private Boolean microphoneCheck;
    @NotEmpty(message = "내용을 입력하세요.")
    private String content;
}
