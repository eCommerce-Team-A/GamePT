package com.example.gamePT.domain.course.controller;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.entity.CourseCreateForm;
import com.example.gamePT.domain.course.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/create")
    public String createCourse() {
        return "course/course_create_form";
    }

    @PostMapping("/create")
    public String createCourse(@Valid CourseCreateForm courseCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "course/course_create_form";
        }
        Course course = this.courseService.createCourse(courseCreateForm.getExpertname(), courseCreateForm.getGameCategoryname(), courseCreateForm.getName(),
                courseCreateForm.getInformation(), courseCreateForm.getPrice());
        return String.format("redirect:/course/detail/%s", course.getId());
    }

    @GetMapping("/detail/{id}")
    public String showCourseDetail(@PathVariable(value = "id")Long id, Model model){
        Course course = this.courseService.findCourseById(id);
        model.addAttribute("course",course);
        return "course_detail";
    }

}
