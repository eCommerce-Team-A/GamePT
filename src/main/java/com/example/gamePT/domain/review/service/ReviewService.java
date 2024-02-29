package com.example.gamePT.domain.review.service;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.repository.CourseRepository;
import com.example.gamePT.domain.review.entity.Review;
import com.example.gamePT.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository; // 임시

    public void create(String content, Integer score){
        Course course = this.courseRepository.getById(2L);
        Review review = Review.builder()
                .author(null)
                .course(course)
                .content(content)
                .score(score)
                .build();

        this.reviewRepository.save(review);
    }

    public List<Review> findByCourseId(Long id){
        return this.reviewRepository.findByCourseId(id);
    }
}
