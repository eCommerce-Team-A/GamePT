package com.example.gamePT.domain.duoArticle.controller;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import com.example.gamePT.domain.duoArticle.enity.DuoArticleForm;
import com.example.gamePT.domain.duoArticle.service.DuoArticleService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/duo")
public class DuoArticleController {
    private final DuoArticleService duoArticleService;

    @GetMapping("/list")
    public String list(Model model) throws IOException {
        String apiUrl = "https://ddragon.leagueoflegends.com/cdn/14.4.1/data/en_US/champion.json";
        RestTemplate restTemplate = new RestTemplate();
        String jsonData = restTemplate.getForObject(apiUrl, String.class);

        // JSON 데이터 파싱
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode championsNode = objectMapper.readTree(jsonData).get("data");

        //이름만 저장
        List<String> championNames = new ArrayList<>();
        Iterator<String> championIterator = championsNode.fieldNames();
        while (championIterator.hasNext()) {
            String championName = championIterator.next();
            championNames.add(championName);
        }

        // 가져온 JSON 데이터를 모델에 저장
        model.addAttribute("championData", championNames);



        List<DuoArticle> duoArticleList = this.duoArticleService.getAllDuoArticles();
        model.addAttribute("duoArticleList", duoArticleList);
        return "duo/list";
    }

    @GetMapping("/create")
    public String create(DuoArticleForm duoArticleForm) {
        return "duo/form";
    }

    @PostMapping("/create")
    public String create(@Valid DuoArticleForm duoArticleForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "duo/list";
        }
        this.duoArticleService.createDuoArticle(duoArticleForm.getMyLine(), duoArticleForm.getFindLine(),
                duoArticleForm.getMicrophoneCheck(), duoArticleForm.getContent());

        return "redirect:/duo/list";
    }
}
