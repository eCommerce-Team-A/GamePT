package com.example.gamePT.domain.course.entity;

import com.example.gamePT.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Course extends BaseEntity {
    private String expertname;  //임시로 (전문가 이름) 삽입 추후 siteUser.expert 객체 추가 필요
    private String gameCategoryname; // 임시로 (카테고리 이름) 삽입 추후 gameCategory 객체추가 필요
    private String name;
    private String information;
    private Integer price;
}
