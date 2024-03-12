package com.example.gamePT.global.riot.service;

import com.example.gamePT.global.riot.entity.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RiotApiService {

    @Value("${secret.myKey}")
    private String myKey;


    //닉네임 입력 시, PuuId 반환
    public String getPuuid(String name, String tag) {
        String tagLineApi = "https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id";
        try {
            String encodedName = URLEncoder.encode(name, "UTF-8"); //공백을 처리하기 위해 인코딩
            String encodedTag = URLEncoder.encode(tag, "UTF-8"); //공백을 처리하기 위해 인코딩
            String url = String.format("%s/%s/%s?api_key=%s", tagLineApi, encodedName, encodedTag, myKey);
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            //요청 성공 시
            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                TagLineDTO tagLineDTO = objectMapper.readValue(connection.getInputStream(), TagLineDTO.class);
                return tagLineDTO.getPuuid();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //소환사 정보 반환
    public SummonerDTO getSummoner(String puuId) {
        String summonerApi = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-puuid";
        try {
            String url = String.format("%s/%s?api_key=%s", summonerApi, puuId, myKey);
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            //요청 성공 시
            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(connection.getInputStream(), SummonerDTO.class);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public LeagueDTO getLeague(String summonerId) {
        String leagueApi = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner";
        try {
            String url = String.format("%s/%s?api_key=%s", leagueApi, summonerId, myKey);
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            //요청 성공 시
            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                List<LeagueDTO> leagueDTOs = objectMapper.readValue(connection.getInputStream(), new TypeReference<List<LeagueDTO>>() {
                });
                if (leagueDTOs.isEmpty()) {
                    return null; //언랭
                } else
                    return leagueDTOs.get(0);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getMatchIds(String puuId) {
        String matchesApi = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid";
        try {
            String url = String.format("%s/%s/ids?start=0&count=20&api_key=%s", matchesApi, puuId, myKey); //최근 20경기
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            //요청 성공 시
            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(connection.getInputStream(), new TypeReference<List<String>>() {
                });
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public MatchInfoDTO getMatchInfo(String matchId) {
        String matchInfoApi = "https://asia.api.riotgames.com/lol/match/v5/matches";
        try {
            String url = String.format("%s/%s?api_key=%s", matchInfoApi, matchId, myKey);
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            //요청 성공 시
            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(connection.getInputStream(), MatchInfoDTO.class);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ParticipantDTO getParticipant(MatchInfoDTO matchInfoDTO, String puuid) {
        List<ParticipantDTO> participantDTOList = matchInfoDTO.getInfo().getParticipants();
        for (ParticipantDTO p : participantDTOList) {
            if (p.getPuuid().equals(puuid)) {
                return p;
            }
        }
        return null;
    }


}
