package com.example.gamePT.domain.duoArticle.controller;


import com.example.gamePT.domain.duoArticle.enity.DuoArticle;
import com.example.gamePT.domain.duoArticle.service.DuoArticleService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
public class DuoArticleControllerTest {

    @Mock
    private DuoArticleService duoArticleService;

    @InjectMocks
    private DuoArticleController duoArticleController;

    private MockMvc mockMvc;

    @Test
    public void 전체게시글출력() throws Exception {
        //새로운 두 DuoArticle 생성하고 List 저장
        List<DuoArticle> mockDuoArticleList = Arrays.asList(new DuoArticle(), new DuoArticle());
        //DuoArticleService 의 메소드를 실행 -> mockDuoArticleList 를 반환
        when(duoArticleService.getAllDuoArticles()).thenReturn(mockDuoArticleList);

        //MockMvc 설정
        mockMvc = MockMvcBuilders.standaloneSetup(duoArticleController).build();

        //test
        mockMvc.perform(get("/duo/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("duoArticleList"))
                .andExpect(view().name("duo/list"))
                .andDo(print());
    }

    @Test
    public void 게시글폼이동() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(duoArticleController).build();
        mockMvc.perform(get("/duo/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("duo/form"))
                .andDo(print());
    }

    @Test
    public void 게시글저장() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(duoArticleController).build();
        mockMvc.perform(post("/duo/create")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("duo/form"))
                .andDo(print());
    }

}
