package com.example.gamePT.domain.home.controller;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.service.CourseService;
import com.example.gamePT.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final CourseService courseService;

    @GetMapping
    public String index(Model model) {

        List<Course> courseList = courseService.getCourseForMain();

        model.addAttribute("courseList",courseList);

        return "home/index";
    }
}
