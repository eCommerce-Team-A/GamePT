package com.example.gamePT.domain.course.controller;

import com.example.gamePT.domain.course.service.CourseService;
import jdk.jfr.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/create")
    public String createCourse (){
        return "course/course_create_form";
    }
}
