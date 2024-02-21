package com.example.gamePT.domain.duoArticle.repository;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

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

        duoarticleRepository.save(duoArticle);

        assertThat(duoArticle).isNotNull();
    }
    @Test
    public void 데이터찾아오기() {
        Optional<DuoArticle> duoArticle1 = duoarticleRepository.findById(1L);
        Optional<DuoArticle> duoArticle2 = duoarticleRepository.findById(2L);
        List<DuoArticle> duoArticleList1 = duoarticleRepository.findByMyLine("상관없음");
        List<DuoArticle> duoArticleList2 = duoarticleRepository.findByMyLine("탑");

        assertThat(duoArticle1).isNotEmpty();
        assertThat(duoArticle2).isEmpty();
        assertThat(duoArticleList1).isNotEmpty();
        assertThat(duoArticleList2).isEmpty();
    }
}
