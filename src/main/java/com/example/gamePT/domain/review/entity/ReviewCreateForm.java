package com.example.gamePT.domain.review.entity;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.user.entity.SiteUser;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewCreateForm {
    private Long courseId;
    @NotEmpty
    private String content;
    @NotNull
    private int score;
}
