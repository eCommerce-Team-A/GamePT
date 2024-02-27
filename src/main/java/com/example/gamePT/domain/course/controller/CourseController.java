package com.example.gamePT.domain.course.controller;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.entity.CourseCreateForm;
import com.example.gamePT.domain.course.service.CourseService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.repository.UserRepository;
import com.example.gamePT.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final UserRepository userRepository; // User 불러오기용 임시 의존성 주입. (createCourse 메서드에서 추후 작업 필요)

    @GetMapping("/create")
    public String createCourse() {
        return "course/course_create_form";
    }

    @PostMapping("/create")
    public String createCourse(@Valid CourseCreateForm courseCreateForm, BindingResult bindingResult, Principal principal) {
        //임시 siteUser 부분 시작
        SiteUser siteUser = null;
        if (principal != null) {
            Optional<SiteUser> _siteUser = this.userRepository.findByUsername(principal.getName());
            if (_siteUser.isPresent()) {
                siteUser = _siteUser.get();
            }
        }
        // 임시 siteUser 부분 끝

        if (bindingResult.hasErrors()) {
            return "course/course_create_form";
        }
        Course course = this.courseService.createCourse(siteUser, courseCreateForm.getGameCategoryname(), courseCreateForm.getName(),
                courseCreateForm.getIntroduce(), courseCreateForm.getCurriculum(), courseCreateForm.getPrice());
        return String.format("redirect:/course/detail/%s", course.getId());
    }

    @GetMapping("/detail/{id}")
    public String showCourseDetail(@PathVariable(value = "id") Long id, Model model) {
        Course course = this.courseService.findCourseById(id);
        List<Course> courseList = this.courseService.findAllCourse();

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
