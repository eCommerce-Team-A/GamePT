package com.example.gamePT.domain.review.entity;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.user.entity.SiteUser;
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
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity {
    @ManyToOne
    private SiteUser author;
    @ManyToOne
    private Course course;
    private int score;
    private String content;
}
