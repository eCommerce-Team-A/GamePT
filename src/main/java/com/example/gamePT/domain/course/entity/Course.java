package com.example.gamePT.domain.course.entity;

import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.context.annotation.Lazy;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Course extends BaseEntity {
    @ManyToOne
    private SiteUser author;
    private String gameCategoryname; // 임시로 (카테고리 이름) 삽입 추후 gameCategory 객체추가 필요
    private String name;
    private String introduce;
    private String curriculum;
    private Integer price;
    private boolean isActive;
    @OneToMany
    private List<SiteUser> buyerList;
}
