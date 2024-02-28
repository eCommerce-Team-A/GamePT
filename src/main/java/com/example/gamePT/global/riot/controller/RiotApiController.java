package com.example.gamePT.global.riot.controller;


import com.example.gamePT.global.riot.entity.LeagueDTO;
import com.example.gamePT.global.riot.entity.MatchInfoDTO;
import com.example.gamePT.global.riot.entity.ParticipantDTO;
import com.example.gamePT.global.riot.entity.SummonerDTO;
import com.example.gamePT.global.riot.service.RiotApiService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @Setter
    @Getter
    public static class ResponseData {
        private int kills;
        private int deaths;
        private int assists;
        private int win;
        private int lose;
        private int teamTotalKill;

        ResponseData(int kills, int deaths, int assists, int win, int lose,int teamTotalKill){
            this.kills = kills;
            this.deaths = deaths;
            this.assists = assists;
            this.win = win;
            this.lose = lose;
            this.teamTotalKill = teamTotalKill;
        }
    }
    @GetMapping("/getMatchesByPuuid/{puuid}")
    @ResponseBody
    public ResponseData getMatchesByPuuid(@PathVariable("puuid") String puuid) throws InterruptedException {

        List<String> matchIds = riotApiService.getMatchIds(puuid);

        int kills = 0;
        int deaths = 0;
        int assists = 0;
        int win = 0;
        int lose = 0;
        int teamTotalKill = 0;

        for (String matchId : riotApiService.getMatchIds(puuid)) {
            MatchInfoDTO info = this.riotApiService.getMatchInfo(matchId);

            ParticipantDTO participantDTO = this.riotApiService.getParticipant(info , puuid);

            kills += participantDTO.getKills();
            deaths += participantDTO.getDeaths();
            assists += participantDTO.getAssists();

            if(participantDTO.isWin()){
                win +=1;
                int teamKill = info.getInfo().getTeams().get(0).isWin() ?
                        info.getInfo().getTeams().get(0).getObjectives().getChampion().getKills() :
                        info.getInfo().getTeams().get(1).getObjectives().getChampion().getKills();

                teamTotalKill += teamKill;
            }else{
                lose +=1;
                int teamKill = !info.getInfo().getTeams().get(0).isWin() ?
                        info.getInfo().getTeams().get(0).getObjectives().getChampion().getKills() :
                        info.getInfo().getTeams().get(1).getObjectives().getChampion().getKills();
                teamTotalKill += teamKill;
            }
        }

        return new ResponseData(kills, deaths, assists, win, lose,teamTotalKill);
    }


}
