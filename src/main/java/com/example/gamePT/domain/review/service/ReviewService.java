package com.example.gamePT.domain.review.service;

import com.example.gamePT.domain.review.entity.Review;
import com.example.gamePT.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public List<Review> FindByCoursename(String coursename) {
        return this.reviewRepository.findByCoursename(coursename);
    }
}
