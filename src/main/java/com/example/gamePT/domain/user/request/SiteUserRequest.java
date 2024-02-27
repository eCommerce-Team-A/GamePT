package com.example.gamePT.domain.user.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class SiteUserRequest {

    @Getter
    @Setter
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

        @NotEmpty(message = "소환사명을 입력해주세요")
        private String gameName;

        @NotEmpty(message = "태그 번호를 입력해 주세요")
        private String tag;

    }

    @Getter
    @Setter
    public static class Login {

        @NotEmpty(message = "아이디를 입력해 주세요")
        private String username;

        @NotEmpty(message = "비밀번호를 입력해 주세요")
        private String password;
    }

    @Getter
    @Setter
    public static class IsUniqueAjax {

        @NotEmpty(message = "잘못된 요청입니다.")
        private String name;

        @NotEmpty(message = "값을 입력해주세요")
        private String value;
    }

    @Getter
    @Setter
    public static class FindUserInfoAjax {

        private String type;

        private String username;

        private String nickname;

        private String email;
    }


}
