package com.example.gamePT.domain.duoArticle.controller;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import com.example.gamePT.domain.duoArticle.service.DuoArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/api/duo")
@RequiredArgsConstructor
public class DuoArticleApiController {
    private final DuoArticleService duoArticleService;

    @GetMapping("/{line}")
    public String getDulArticlesByMyLine(@PathVariable(value = "line") String line, Model model) {
        List<DuoArticle> duoArticleList = new ArrayList<>();
        if (line.equals("fill")) {
            duoArticleList = this.duoArticleService.getAllDuoArticles();
        }
        duoArticleList = this.duoArticleService.getDulArticlesByMyLine(line);

        model.addAttribute("duoArticleList", duoArticleList);


        return "duo/list::#duoArticle1";

//        if (!duoArticleList.isEmpty()) {
//
//            return duoArticleList;
//        } else {
//            return null;
//        }
    }
}
//승인 시 기준, 자주 묻는 질문, 진행중
