package com.example.gamePT.domain.course.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseCreateForm {//임시로 (전문가 이름) 삽입 추후 siteUser .expert 객체 추가 필요
    private String name;
    private String introduce;
    private String curriculum;
    private Integer price;
    private int discountRate;
}
