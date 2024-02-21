package com.example.gamePT.domain.duoArticle.repository;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@SpringBootTest
public class DuoArticleRepositoryTest {

    @Autowired
    private DuoArticleRepository duoarticleRepository;

    @Test
    public void 데이터넣어보기() {
        final DuoArticle duoArticle = DuoArticle.builder()
                .id(1L)
                .myLine("상관없음")
                .findLine("상관없음")
                .microphoneCheck(true)
                .content("듀오구합니다.")
                .createDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();
        

    }
}
