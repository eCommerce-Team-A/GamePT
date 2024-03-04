package com.example.gamePT.domain.duoArticle.controller;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import com.example.gamePT.domain.duoArticle.service.DuoArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class DuoArticleApiController {

    private final DuoArticleService duoArticleService;

    @GetMapping("/getDuoArticleList/{line}")
    public String getDuoArticlesByMyLine(@PathVariable(value = "line") String line, Model model) {
        List<DuoArticle> duoArticleList;
        if (line.equals("fill")) {
            duoArticleList = this.duoArticleService.getAllDuoArticles();
        } else {
            duoArticleList = this.duoArticleService.getDulArticlesByMyLine(line);
        }
        model.addAttribute("duoArticleList", duoArticleList);
        return "duo/list::#duoArticle1";
    }

    @GetMapping("/getDouArticle/{id}")
    public ResponseEntity<DuoArticle> getDuoArticle(@PathVariable(value = "id")Long id) {
        DuoArticle duoArticle =  this.duoArticleService.getDuoArticleById(id);
        System.out.println(duoArticle);
        if (duoArticle == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return  new ResponseEntity<>(duoArticle, HttpStatus.OK);
        }
    }
}

