package com.example.gamePT.domain.course.controller;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.entity.CourseCreateForm;
import com.example.gamePT.domain.course.service.CourseService;
import com.example.gamePT.domain.review.entity.Review;
import com.example.gamePT.domain.review.service.ReviewService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;
    private final ReviewService reviewService;

    //강의 등록
    @GetMapping("/create")
    public String createCourse() {
        return "course/course_create_form";
    }

    // 강의 등록
    @PostMapping("/create")
    public String createCourse(@Valid CourseCreateForm courseCreateForm, BindingResult bindingResult, Principal principal) {
        SiteUser author = this.userService.findByUsername(principal.getName());
        if (bindingResult.hasErrors()) {
            return "course/course_create_form";
        }
        Course course = this.courseService.createCourse(author, courseCreateForm.getGameCategoryname(), courseCreateForm.getName(),
                courseCreateForm.getIntroduce(), courseCreateForm.getCurriculum(), courseCreateForm.getPrice());

        return String.format("redirect:/course/detail/%s", course.getId());
    }

    //강의 상세 페이지
    @GetMapping("/detail/{id}")
    public String showCourseDetail(@PathVariable(value = "id") Long id, Model model) {
        Course course = this.courseService.findCourseById(id);
        List<Course> courseList = this.courseService.findAllCourse();
        List<Review> reviewList = this.reviewService.findByCourseId(id);

        model.addAttribute("reviewList", reviewList);
        model.addAttribute("courseList", courseList);
        model.addAttribute("course", course);

        return "course/course_detail";
    }


    @GetMapping("/list")
    public String courseList(Model model) {
        List<Course> courseList = this.courseService.findAllCourse();
        model.addAttribute("courseList", courseList);

        return "course/course_list";
    }
}
