package com.example.gamePT.domain.career.entity;

import com.example.gamePT.domain.expert.entity.Expert;
import com.example.gamePT.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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
    private String category;
    private String icon;
    private String color;
    private String content;
    @ManyToOne
    private Expert expert;
}
