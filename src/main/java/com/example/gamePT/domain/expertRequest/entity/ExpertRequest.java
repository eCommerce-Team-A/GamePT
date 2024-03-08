package com.example.gamePT.domain.expertRequest.entity;

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
public class ExpertRequest extends BaseEntity {
    private String userName;
    private String content;
}
