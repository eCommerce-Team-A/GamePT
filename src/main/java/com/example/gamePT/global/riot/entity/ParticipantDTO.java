package com.example.gamePT.global.riot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true) //json데이터를 사용할 때, 클래스에 정의되지 않은 속성은 무시
public class ParticipantDTO { //해당경기정보
    private String puuid;
    private String championName;
    private boolean win;
    private int kills;
    private int deaths;
    private int assists;
}
