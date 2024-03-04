package com.example.gamePT.domain.review.repository;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByCourseIdOrderByCreateDateDesc(Long id);
}