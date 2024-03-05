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
    private String username;
    private String puuid;
    private String content;
    private String gameName;
    private String tag;
    private int profileIconId;
    private String tier;
    private String rank;
    private int wins;
    private int losses;
//    private double avgKills;
//    private double avgDeaths;
//    private double avgAssists;

    public double getWinRate() {
        if (losses == 0) {
            return 100.0;
        }
        return ((double) wins / (wins + losses)) * 100.0;
    }

    public double getLossRate() {
        if (wins == 0) {
            return 100.0;
        }
        return ((double) losses / (wins + losses)) * 100.0;
    }
}
