package com.example.gamePT.global.riot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true) //json데이터를 사용할 때, 클래스에 정의되지 않은 속성은 무시
public class LeagueDTO { //랭크정보
    private String leagueId;
    private String summonerId;
    private String summonerName;
    private String tier;
    private String rank;
    private int wins;
    private int losses;
}
