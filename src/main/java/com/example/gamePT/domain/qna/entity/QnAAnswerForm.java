package com.example.gamePT.domain.qna.entity;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class QnAAnswerForm {

    @NotEmpty(message = "답변 내용은 필수 입니다.")
    private String answer;

}
