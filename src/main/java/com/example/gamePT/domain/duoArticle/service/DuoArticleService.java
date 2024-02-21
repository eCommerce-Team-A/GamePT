package com.example.gamePT.domain.duoArticle.service;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import com.example.gamePT.domain.duoArticle.repository.DuoArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DuoArticleService {
    private final DuoArticleRepository duoArticleRepository;

    public List<DuoArticle> getAllDuoArticles() {
        return duoArticleRepository.findAll();
    }
}
