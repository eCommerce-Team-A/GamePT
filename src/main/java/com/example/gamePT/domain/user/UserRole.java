package com.example.gamePT.domain.user;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("ROLE_ADMIN"),
    EXPERT("ROLE_EXPERT"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
