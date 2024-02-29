package com.example.gamePT.domain.review.controller;

import com.example.gamePT.domain.review.entity.ReviewCreateForm;
import com.example.gamePT.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/create")
    @ResponseBody
    public ReviewCreateForm create(@RequestBody ReviewCreateForm reviewCreateForm) {
        this.reviewService.create(reviewCreateForm.getContent(), reviewCreateForm.getScore());

        return reviewCreateForm;
    }
}
