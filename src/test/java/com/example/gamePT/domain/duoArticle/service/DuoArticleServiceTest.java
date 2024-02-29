package com.example.gamePT.domain.duoArticle.service;

import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import com.example.gamePT.domain.duoArticle.repository.DuoArticleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class DuoArticleServiceTest {

    @Mock
    private DuoArticleRepository duoArticleRepository;

    @InjectMocks
    private DuoArticleService duoArticleService;

    @Test
    public void 전체게시글가져오기() {
        List<DuoArticle> duoArticleList = duoArticleService.getAllDuoArticles();

        assertThat(duoArticleList).isNotEmpty();
        assertThat(duoArticleList).hasSize(1);
    }

    @Test
    public void 게시글등록하기() {
        String myLine = "상관없음";
        String findLine = "상관없음";
        Boolean microphoneCheck = true;
        String content = "듀오구합니다.";
        DuoArticle duoArticle = this.duoArticleService.createDuoArticle(myLine, findLine, microphoneCheck, content,
                "ds","ds","1","1", 1,1);

        assertThat(duoArticle).isNotNull();
        assertThat(duoArticle.getMyLine()).isEqualTo(myLine);
        assertThat(duoArticle.getFindLine()).isEqualTo(findLine);
        assertThat(duoArticle.getMicrophoneCheck()).isTrue();
        assertThat(duoArticle.getMyLine()).isEqualTo(myLine);
    }
}
