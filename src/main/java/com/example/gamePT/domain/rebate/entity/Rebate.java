package com.example.gamePT.domain.rebate.entity;

import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Rebate extends BaseEntity {
    @Column(name = "a1")
    int authorId;
    @Column(name = "y1")
    int year;
    @Column(name = "m1")
    int month;
    @Column(name = "t1")
    int totalMoney;
}
