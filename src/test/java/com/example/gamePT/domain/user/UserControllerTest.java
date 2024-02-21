package com.example.gamePT.domain.user;

import com.example.gamePT.domain.user.controller.UserController;
import com.example.gamePT.domain.user.repository.UserRepository;
import com.example.gamePT.domain.user.request.SiteUserRequest;
import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.Errors;

import java.util.stream.Stream;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @SpyBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired(required = true)
    private Gson gson;


    @Transactional
    @DisplayName("회원가입 성공")
    @Test
    public void signup_Created() throws Exception {

        // given
        SiteUserRequest.Signup signup = SiteUserRequest.Signup.builder()
                .username("user1")
                .password("1234")
                .passwordConfirm("1234")
                .email("email@gmail.com")
                .nickname("홍길동")
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/user/signup")
                        .content(gson.toJson(signup))
                        .contentType(MediaType.APPLICATION_JSON).with(csrf())
        ).andDo(print());

        resultActions.andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"))
                .andExpect(redirectedUrl("/"));
    }

    @Transactional
    @DisplayName("회원가입 실패")
    @ParameterizedTest
    @MethodSource("validForm")
    public void signup_false(String username, String password, String passwordConfirm, String nickname, String email,String errorMsg) throws Exception {

        // given
        SiteUserRequest.Signup signup = SiteUserRequest.Signup.builder()
                .username(username)
                .password(password)
                .passwordConfirm(passwordConfirm)
                .email(email)
                .nickname(nickname)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/user/signup")
                        .content(gson.toJson(signup))
                        .contentType(MediaType.APPLICATION_JSON).with(csrf())
        ).andDo(print());

        resultActions.andExpect(status().isOk())
                .andExpect(view().name("/user/signup"))
                .andExpect(status().isUnauthorized())
                .andExpect(status().reason(containsString(errorMsg)))
                .andExpect(unauthenticated());;
    }

    static Stream<Arguments> validForm() {
        return Stream.of(
                // FormData 가 올바르지 않는 경우
                Arguments.arguments("","1234","1234","홍길동","email@gmail.com", "1"),
                Arguments.arguments("user1","","1234","홍길동","email@gmail.com", "1"),
                Arguments.arguments("user1","1234","","홍길동","email@gmail.com", "1"),
                Arguments.arguments("user1","1234","1234","","email@gmail.com", "1"),
                Arguments.arguments("user1","1234","1234","홍길동","", "1")

        );
    }

}
