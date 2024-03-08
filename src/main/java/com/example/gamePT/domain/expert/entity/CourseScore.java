package com.example.gamePT.domain.expert.entity;

import com.example.gamePT.domain.course.entity.Course;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseScore {
    Course course;
    String avg;
    public CourseScore(String avg, Course course) {
        this.avg = avg;
        this.course = course;
    }
}
