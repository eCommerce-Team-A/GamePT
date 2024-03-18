package com.example.gamePT.domain.course.controller;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.entity.CourseCreateForm;
import com.example.gamePT.domain.course.service.CourseService;
import com.example.gamePT.domain.expert.entity.Expert;
import com.example.gamePT.domain.expert.entity.SiteUserWithImg;
import com.example.gamePT.domain.expert.service.ExpertService;
import com.example.gamePT.domain.image.service.ImageService;
import com.example.gamePT.domain.orderItem.service.OrderItemService;
import com.example.gamePT.domain.review.entity.Review;
import com.example.gamePT.domain.review.service.ReviewService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;
    private final ReviewService reviewService;
    private final ExpertService expertService;
    private final OrderItemService orderItemService;
    private final ImageService imageService;

    //강의 등록
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createCourse(Principal principal) {
        if (principal == null || !"Expert".equals(this.userService.findByUsername(principal.getName()).getAuthorization())) {
            return "redirect:/";
        }
        return "course/course_create";
    }

    // 강의 등록
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createCourse(@Valid CourseCreateForm courseCreateForm, BindingResult bindingResult, Principal principal,
                               @RequestParam(value = "introduceImg") MultipartFile introduceImg, @RequestParam(value = "curriculumImg") MultipartFile curriculumImg) throws IOException {
        SiteUser author = this.userService.findByUsername(principal.getName());
        if (bindingResult.hasErrors()) {
            return "course/course_create";
        }
        Course course = this.courseService.createCourse(author, courseCreateForm.getName(),
                courseCreateForm.getIntroduce(), courseCreateForm.getCurriculum(), courseCreateForm.getPrice(), courseCreateForm.getDiscountRate());
        this.imageService.saveIntroduceImg(course, introduceImg);
        this.imageService.saveCurriculumImg(course, curriculumImg);

        return String.format("redirect:/course/detail/%s", course.getId());
    }

    //강의 상세 페이지
    @GetMapping("/detail/{id}")
    public String showCourseDetail(@PathVariable(value = "id") Long id, @RequestParam(value = "page", defaultValue = "0") int page, Model model, Principal principal) {
        Course course = this.courseService.findCourseById(id);

        List<Course> courseListByAuthor = this.courseService.findCourseByAuthorId(course.getAuthor().getId());
        Page<Review> reviewList = this.reviewService.findByCourseId(id, page);
        boolean isPurchased = false;
        if (principal != null) {
            SiteUser siteUser = this.userService.findByUsername(principal.getName());
            if (orderItemService.isPurchasedBySiteUserId(siteUser.getId(), course.getId())) {
                isPurchased = true;
            }
        }
        SiteUserWithImg expertData = toDto(course.getAuthor());

        String introduceImg = this.imageService.getIntroduceImg(course.getId());
        String curriculumImg = this.imageService.getCurriculumImg(course.getId());


        model.addAttribute("expertData", expertData);
        model.addAttribute("isPurchased", isPurchased);
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("courseListByAuthor", courseListByAuthor);
        model.addAttribute("course", course);

        model.addAttribute("introduceImg",introduceImg);
        model.addAttribute("curriculumImg",curriculumImg);

        return "course/course_detail";
    }

    public SiteUserWithImg toDto(SiteUser expertUser) {

        SiteUserWithImg SiteUserWithImgList = SiteUserWithImg.builder()
                .siteUser(expertUser)
                .img(this.userService.getProfileImg(expertUser.getId()))
                .expert(this.expertService.getExpertBySiteUserId(expertUser.getId()))
                .build();

        return SiteUserWithImgList;
    }

    @GetMapping("/list")
    public String courseList(Model model, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Course> courseList = this.courseService.findAllByKeyword(page, kw);
        model.addAttribute("courseList", courseList);
        model.addAttribute("kw", kw);

        return "course/course_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update/{id}")
    public String updateCourse(@PathVariable(value = "id") Long id, Model model, Principal principal) {
        Course course = this.courseService.findCourseById(id);
        if (principal == null || !principal.getName().equals(course.getAuthor().getUsername())) {
            return "redirect:/";
        }
        model.addAttribute("course", course);

        return "course/course_update";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{id}")
    public String updateCourse(@PathVariable(value = "id") Long id, @Valid CourseCreateForm courseCreateForm,
                               BindingResult bindingResult, Model model) {
        Course course = this.courseService.updateCourse(id, courseCreateForm.getName(),
                courseCreateForm.getIntroduce(), courseCreateForm.getCurriculum(), courseCreateForm.getPrice(), courseCreateForm.getDiscountRate());

        return "redirect:/course/detail/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/disable/{id}")
    public String disalbeCourse(@PathVariable(value = "id") Long id, Principal principal) {

        Course course = this.courseService.findCourseById(id);
        if (principal == null || !principal.getName().equals(course.getAuthor().getUsername())) {
            return "redirect:/";
        }
        this.courseService.disalbeCourse(id);
        return "redirect:/course/detail/" + id;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/active/{id}")
    public String activeCourse(@PathVariable(value = "id") Long id, Principal principal) {
        Course course = this.courseService.findCourseById(id);
        if (principal == null || !principal.getName().equals(course.getAuthor().getUsername())) {
            return "redirect:/";
        }
        this.courseService.activeCourse(id);

        return "redirect:/course/detail/" + id;
    }
}
