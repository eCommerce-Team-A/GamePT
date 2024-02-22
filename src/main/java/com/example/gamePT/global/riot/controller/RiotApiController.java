package com.example.gamePT.global.riot.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class RiotApiController {

    @GetMapping("/champions")
    public String getChampionData(Model model) {
        // JSON 데이터를 가져오기 위한 RestTemplate 사용
        String apiUrl = "https://ddragon.leagueoflegends.com/cdn/14.4.1/data/en_US/champion.json";
        RestTemplate restTemplate = new RestTemplate();
        String jsonData = restTemplate.getForObject(apiUrl, String.class);

        // 가져온 JSON 데이터를 모델에 저장
        model.addAttribute("championData", jsonData);

        // 뷰의 이름을 반환 (Thymeleaf 템플릿 엔진 사용)
        return "champion";
    }
}
