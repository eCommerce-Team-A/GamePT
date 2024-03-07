package com.example.gamePT.domain.duoArticle.service;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import com.example.gamePT.domain.duoArticle.repository.DuoArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DuoArticleService {
    private final DuoArticleRepository duoArticleRepository;

    public List<DuoArticle> getAllDuoArticles() {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Sort sort = Sort.by(sorts);
        return duoArticleRepository.findAll(sort);
    }

    public List<DuoArticle> getDuoArticlesByMyLine(String line) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Sort sort = Sort.by(sorts);
        return duoArticleRepository.findByMyLine(line, sort);
    }

    public DuoArticle getDuoArticleById(Long id) {
        Optional<DuoArticle> duoArticle = this.duoArticleRepository.findById(id);
        if (duoArticle.isEmpty()) {
            return null;
        }
        return duoArticle.get();
    }

    public DuoArticle createDuoArticle(String myLine, String findLine, Boolean microphoneCheck, String content, String username,
                                       String puuid, String gameName, String tag, int profileIconId, String tier, String rank, int wins, int losses) {
        DuoArticle duoArticle = DuoArticle.builder()
                .myLine(myLine)
                .findLine(findLine)
                .microphoneCheck(microphoneCheck)
                .content(content)
                .username(username)
                .puuid(puuid)
                .gameName(gameName)
                .tag(tag)
                .profileIconId(profileIconId)
                .tier(tier)
                .rank(rank)
                .wins(wins)
                .losses(losses)
                .build();
        this.duoArticleRepository.save(duoArticle);
        return duoArticle;
    }

    public void deleteDuoArticle(DuoArticle duoArticle) {
        this.duoArticleRepository.delete(duoArticle);
    }

    public void modifyDuoArticle(DuoArticle duoArticle, String myLine, String findLine, Boolean microphoneCheck, String content) {
        DuoArticle modifyDuoArticle = duoArticle.toBuilder()
                .myLine(myLine)
                .findLine(findLine)
                .microphoneCheck(microphoneCheck)
                .content(content)
                .build();
        this.duoArticleRepository.save(modifyDuoArticle);
    }

}
