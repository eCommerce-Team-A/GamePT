package com.example.gamePT.global.riot.service;

import com.example.gamePT.global.riot.entity.SummonerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class RiotApiService {


    private String myKey = "RGAPI-fedd0ae5-214d-4ef3-8d8b-1359a2261f70";

    public SummonerDTO getSummoner(String name) {
        String summonerApi = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name";
        try {
            String encodedName = URLEncoder.encode(name, "UTF-8"); //공백을 처리하기 위해 인코딩
            String url = String.format("%s/%s?api_key=%s", summonerApi, encodedName, myKey);
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            //요청 성공 시
            if (responseCode == HttpURLConnection.HTTP_OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                SummonerDTO summonerDTO = objectMapper.readValue(connection.getInputStream(), SummonerDTO.class);
                return summonerDTO;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
