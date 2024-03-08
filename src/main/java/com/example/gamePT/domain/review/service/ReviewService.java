package com.example.gamePT.domain.review.service;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.repository.CourseRepository;
import com.example.gamePT.domain.review.entity.Review;
import com.example.gamePT.domain.review.repository.ReviewRepository;
import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final CourseRepository courseRepository;

    public Page<Review> create(SiteUser author, Long courseId, String content, Integer score, int page) {
        Course course = this.courseRepository.getById(courseId);
        Review review = Review.builder()
                .author(author)
                .course(course)
                .content(content)
                .score(score)
                .build();
        this.reviewRepository.save(review);
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return this.reviewRepository.findByCourseIdOrderByCreateDateDesc(courseId, pageable);
    }

    public Page<Review> findByCourseId(Long id, int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return this.reviewRepository.findByCourseIdOrderByCreateDateDesc(id, pageable);
    }

    public String getScoreAvg(Long courseId) {
        List<Integer> scoreList = new ArrayList<>();
        List<Review> reviewList = this.reviewRepository.findByCourseId(courseId);
        for (Review review : reviewList) {
            scoreList.add(review.getScore());
        }
        IntSummaryStatistics statistics = scoreList
                .stream()
                .mapToInt(num -> num)
                .summaryStatistics();
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return String.format("%s (%d)", decimalFormat.format(statistics.getAverage()), reviewList.size());
    }
}
