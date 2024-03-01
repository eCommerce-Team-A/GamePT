package com.example.gamePT.domain.review.controller;

import com.example.gamePT.domain.review.entity.Review;
import com.example.gamePT.domain.review.entity.ReviewCreateForm;
import com.example.gamePT.domain.review.service.ReviewService;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.domain.user.service.UserService;
import com.fasterxml.jackson.core.JsonParser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping("/create")
    public List<Review> create(@RequestBody ReviewCreateForm reviewCreateForm, Principal principal) {
        SiteUser author = this.userService.findByUsername(principal.getName());
        List<Review> reviewList = this.reviewService.create(author,reviewCreateForm.getCourseId(), reviewCreateForm.getContent(),
                reviewCreateForm.getScore());

        return reviewList;
    }
}
