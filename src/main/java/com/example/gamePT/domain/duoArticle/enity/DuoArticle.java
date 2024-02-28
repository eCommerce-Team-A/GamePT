package com.example.gamePT.domain.duoArticle.enity;

import com.example.gamePT.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DuoArticle extends BaseEntity {
    private String myLine;
    private String findLine;
    private Boolean microphoneCheck;
    private String content;
    private String gameName;
    private String tier;
    private int wins;
    private int losses;
    private double avgKills;
    private double avgDeaths;
    private double avgAssists;
}
