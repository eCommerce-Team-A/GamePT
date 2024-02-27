package com.example.gamePT.domain.user.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class SiteUserResponse {

    @Getter
    @Setter
    @Builder
    public static class AjaxRes {

        private Boolean isSuccess;

        private String message;

    }

}
