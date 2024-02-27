package com.example.gamePT.global.riot.controller;


import com.example.gamePT.global.riot.entity.LeagueDTO;
import com.example.gamePT.global.riot.entity.SummonerDTO;
import com.example.gamePT.global.riot.service.RiotApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/riotApiController")
public class RiotApiController {

    private final RiotApiService riotApiService;

    @GetMapping("/getLeagueDataByPuuid/{puuid}")
    @ResponseBody
    public LeagueDTO getLeagueDataByPuuid(@PathVariable("puuid") String puuid){

        SummonerDTO summoner = riotApiService.getSummoner(puuid);

        return riotApiService.getLeague(summoner.getId());
    }



}
