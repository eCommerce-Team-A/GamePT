package com.example.gamePT.domain.duoArticle.controller;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import com.example.gamePT.domain.duoArticle.service.DuoArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/duo")
public class DuoArticleController {
    private final DuoArticleService duoArticleService;

    @GetMapping("/list")
    public String list(Model model) throws IOException {
        List<DuoArticle> duoArticleList = this.duoArticleService.getAllDuoArticles();
        model.addAttribute("duoArticleList", duoArticleList);
        return "duo/list";
    }

    @PostMapping("/create")
    public String create(@RequestParam("myLine") String myLine, @RequestParam("findLine") String findLine,
                         @RequestParam(name = "microphoneCheck", defaultValue = "false") boolean microphoneCheck,
                         @RequestParam("content") String content) {

        this.duoArticleService.createDuoArticle(myLine, findLine, microphoneCheck, content);

        return "redirect:/duo/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        DuoArticle duoArticle = this.duoArticleService.getDuoArticleById(id);
        this.duoArticleService.deleteDuoArticle(duoArticle);
        return "redirect:/duo/list";
    }

    @GetMapping("/api/create")
    public String apiCreate() {
        return "duo/api_test_form";
    }


}
