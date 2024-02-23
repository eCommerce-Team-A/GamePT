package com.example.gamePT.domain.duoArticle.service;

import com.example.gamePT.global.riot.entity.SummonerDTO;
import com.example.gamePT.global.riot.service.RiotApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RiotApiServiceTest {

    @Autowired
    RiotApiService riotApiService;

    @Test
    public void test() {
        SummonerDTO summonerDTO = this.riotApiService.getSummoner("hideonbush");
        System.out.printf("아이디 -> " + summonerDTO.getAccountId() + "\n");
        System.out.printf("어카운트아이디 -> " + summonerDTO.getAccountId() + "\n");
        System.out.printf("Puu아이디 -> " + summonerDTO.getPuuid() + "\n");
        System.out.printf("소환사명 -> " + summonerDTO.getName() + "\n");
        System.out.printf("프로필아이디 -> " + summonerDTO.getProfileIconId() + "\n");
        System.out.printf("개정날짜 -> " + summonerDTO.getRevisionDate() + "\n");
        System.out.printf("레벨 -> " + summonerDTO.getSummonerLevel() + "\n");
        assertThat(summonerDTO).isNotNull();
    }
}
