package com.example.gamePT.domain.duoArticle.service;

import com.example.gamePT.global.riot.entity.LeagueDTO;
import com.example.gamePT.global.riot.entity.SummonerDTO;
import com.example.gamePT.global.riot.service.RiotApiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RiotApiServiceTest {

    @Autowired
    RiotApiService riotApiService;

    @Test
    public void test() {
        //puuId
        String puuid = this.riotApiService.getPuuid("5 빈", "KR1");
        System.out.printf("PUU아이디 -> " + puuid + "\n");
        assertThat(puuid).isNotNull();

        //소환사 정보
        SummonerDTO summonerDTO = this.riotApiService.getSummoner(puuid);
        System.out.printf("소환사아이디 -> " + summonerDTO.getId() + "\n");
        assertThat(summonerDTO).isNotNull();

        //랭크 정보
        LeagueDTO leagueDTO = this.riotApiService.getLeague(summonerDTO.getId());
        if (leagueDTO == null) {
            System.out.printf("랭크 -> " + "unranked");
        } else {
            System.out.printf("랭크 -> " + leagueDTO.getTier() + "_" + leagueDTO.getRank() + "\n");
            System.out.printf(leagueDTO.getWins() + "승/" + leagueDTO.getLosses() + "패 " + "\n");
        }

        //최근20경기
        List<String> matches = this.riotApiService.getMatchIds(puuid);
        System.out.printf("매치 -> " + Arrays.toString(matches.toArray()));
        assertThat(matches).isNotNull();
    }

    @Test
    //게임종류(랭크, 칼바람, ...), 챔피언, kda, 승/패
    public void 경기정보가져오기() {
//        String puuid = this.riotApiService.getPuuid("5 빈", "KR1");
//        MatchInfoDTO matchInfoDTO = this.riotApiService.getMatchInfo("KR_6979611846");
//        System.out.printf(matchInfoDTO.getInfo().getGameMode());
//        List<ParticipantDTO> participantDTOList = matchInfoDTO.getInfo().getParticipants();
//        ParticipantDTO participantDTO = new ParticipantDTO();
//        for (ParticipantDTO p : participantDTOList) {
//            if (p.getPuuid().equals(puuid)) {
//                participantDTO = p;
//                break;
//            }
//        }
//        System.out.printf(participantDTO.getChampionName());
        int a = 1;
        System.out.println(a);
    }
}
