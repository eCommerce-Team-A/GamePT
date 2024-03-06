package com.example.gamePT.domain.review.service;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.repository.CourseRepository;
import com.example.gamePT.domain.review.entity.Review;
import com.example.gamePT.domain.review.repository.ReviewRepository;
import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;

    public List<Review> create(SiteUser author, Long courseId, String content, Integer score) {
        Course course = this.courseRepository.getById(courseId);
        Review review = Review.builder()
                .author(author)
                .course(course)
                .content(content)
                .score(score)
                .build();
        this.reviewRepository.save(review);
        return this.reviewRepository.findByCourseIdOrderByCreateDateDesc(courseId);
    }

    public List<Review> findByCourseId(Long id) {
        return this.reviewRepository.findByCourseIdOrderByCreateDateDesc(id);
    }

    public String getScoreAvg(Long courseId) {
        List<Integer> scoreList = new ArrayList<>();
        List<Review> reviewList = this.reviewRepository.findByCourseIdOrderByCreateDateDesc(courseId);
        for (Review review : reviewList) {
            scoreList.add(review.getScore());
        }
        IntSummaryStatistics statistics = scoreList
                .stream()
                .mapToInt(num -> num)
                .summaryStatistics();
        return String.format("%s (%d)", statistics.getAverage(), reviewList.size());
    }
}
