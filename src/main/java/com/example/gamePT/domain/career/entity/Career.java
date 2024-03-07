package com.example.gamePT.domain.career.entity;

import com.example.gamePT.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Career extends BaseEntity {
    private String introduce;
    private String category;
    private String content;
    private String username;
}
