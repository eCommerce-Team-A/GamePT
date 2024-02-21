package com.example.gamePT.domain.duoArticle.service;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class DuoArticleServiceTest {

    @Autowired
    private DuoArticleService duoArticleService;
    @Test
    public void 전체게시글가져오기() {
        List<DuoArticle> duoArticleList = duoArticleService.getAllDuoArticles();

        assertThat(duoArticleList).isNotEmpty();
        assertThat(duoArticleList).hasSize(1);
    }
}
