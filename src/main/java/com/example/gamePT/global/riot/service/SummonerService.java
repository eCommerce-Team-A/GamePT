package com.example.gamePT.global.riot.service;

import com.example.gamePT.global.riot.Summoner;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

@Service
@PropertySource(ignoreResourceNotFound = false, value = "classpath:riotApiKey.properties")
public class SummonerService {

    private ObjectMapper objectMapper = new ObjectMapper();

    private String mykey = "RGAPI-c4891bcb-a74d-44e6-b19c-1334c7ba26d3";

    public Summoner callRiotAPISummonerByName(String summonerName){

        Summoner result;

        String serverUrl = "https://kr.api.riotgames.com";

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(serverUrl + "/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + mykey);

            HttpResponse response = client.execute(request);

            if(response.getStatusLine().getStatusCode() != 200){
                return null;
            }

            HttpEntity entity = response.getEntity();
            result = objectMapper.readValue(entity.getContent(), Summoner.class);

        } catch (IOException e){
            e.printStackTrace();
            return null;
        }

        return result;
    }
}
