package com.example.gamePT.domain.duoArticle.controller;

import com.example.gamePT.domain.duoArticle.service.DuoArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DuoArticleController {
    private final DuoArticleService duoArticleService;
}
