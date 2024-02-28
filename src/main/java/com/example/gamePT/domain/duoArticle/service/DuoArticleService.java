package com.example.gamePT.domain.duoArticle.service;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import com.example.gamePT.domain.duoArticle.repository.DuoArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DuoArticleService {
    private final DuoArticleRepository duoArticleRepository;

    public List<DuoArticle> getAllDuoArticles() {
        return duoArticleRepository.findAll();
    }

    public DuoArticle getDuoArticleById(Long id) {
        Optional<DuoArticle> duoArticle = this.duoArticleRepository.findById(id);
        if (duoArticle.isEmpty()) {
            return null;
        }
        return duoArticle.get();
    }

    public DuoArticle createDuoArticle(String myLine, String findLine, Boolean microphoneCheck, String content,
                                       String puuid, String gameName, String tier, String rank, int wins, int losses, int total) {
        DuoArticle duoArticle = DuoArticle.builder()
                .myLine(myLine)
                .findLine(findLine)
                .microphoneCheck(microphoneCheck)
                .content(content)
                .puuid(puuid)
                .gameName(gameName)
                .tier(tier)
                .rank(rank)
                .wins(wins)
                .losses(losses)
                .total(total)
                .build();
        this.duoArticleRepository.save(duoArticle);
        return duoArticle;
    }

    public void deleteDuoArticle(DuoArticle duoArticle) {
        this.duoArticleRepository.delete(duoArticle);
    }


}
