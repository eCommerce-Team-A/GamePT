package com.example.gamePT.domain.review;

import com.example.gamePT.domain.review.entity.Review;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired(required = true)
    private Gson gson;

    @Transactional
    @DisplayName("리뷰 생성 성공")
    @Test
    public void review_created() throws Exception{

        Review review = Review.builder()
                .author(null)
                .course(null)
                .score(5)
                .content("테스트 내용")
                .build();

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/review/create")
                        .content(gson.toJson(review))
                        .contentType(MediaType.APPLICATION_JSON).with(csrf())
        ).andDo(print());
    }
}
