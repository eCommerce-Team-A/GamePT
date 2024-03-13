package com.example.gamePT.domain.review.controller;

import com.example.gamePT.domain.orderItem.service.OrderItemService;
import com.example.gamePT.domain.review.entity.Review;
import com.example.gamePT.domain.review.entity.ReviewCreateForm;
import com.example.gamePT.domain.review.entity.ReviewPageNum;
import com.example.gamePT.domain.review.service.ReviewService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import com.fasterxml.jackson.core.JsonParser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@RequestBody ReviewCreateForm reviewCreateForm, Principal principal,
                         Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        SiteUser author = this.userService.findByUsername(principal.getName());
        Page<Review> reviewList = this.reviewService.create(author, reviewCreateForm.getCourseId(), reviewCreateForm.getContent(),
                reviewCreateForm.getScore(), page);
        model.addAttribute("reviewList", reviewList);

        return "course/course_detail::#reviews";
    }

    @PostMapping("/list/{id}")
    public String reviewList(@PathVariable(value = "id") Long id, @RequestBody ReviewPageNum reviewPageNum,
                             Model model) {
        Page<Review> reviewList = this.reviewService.findByCourseId(id, reviewPageNum.getPage());
        model.addAttribute("reviewList",reviewList);

        return "course/course_detail::#reviews";

    }
}
