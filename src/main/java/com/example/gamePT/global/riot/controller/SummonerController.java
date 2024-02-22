package com.example.gamePT.global.riot.controller;

import com.example.gamePT.global.riot.Summoner;
import com.example.gamePT.global.riot.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class SummonerController {

    private final SummonerService summonerService;

    @PostMapping(value = "/summonerByName")
    @ResponseBody
    public Summoner callSummonerByName(String summonerName){

        summonerName = summonerName.replaceAll(" ","%20");

        Summoner apiResult = summonerService.callRiotAPISummonerByName(summonerName);

        return apiResult;
    }
}