package com.example.gamePT.domain.user.request;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
public class SiteUserRequest {

    @Getter
    @Builder
    public static class Signup {

        @NotEmpty(message = "로그인 아이디를 입력해 주세요")
        private String username;

        @NotEmpty(message = "비밀번호를 입력해 주세요")
        private String password;

        @NotEmpty(message = "비밀번호 확인을 입력해 주세요")
        private String passwordConfirm;

        @NotEmpty(message = "닉네임을 입력해 주세요")
        private String nickname;

        @NotEmpty(message = "이메일을 입력해 주세요")
        private String email;
    }

    @Getter
    public static class Login {

        @NotNull
        private String username;

        @NotNull
        private String password;
    }


}
