package com.example.gamePT.domain.qna.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class QnAAnswerForm {
    private String answer;
}
