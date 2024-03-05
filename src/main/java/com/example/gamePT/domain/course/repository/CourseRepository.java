package com.example.gamePT.domain.course.repository;

import com.example.gamePT.domain.course.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findCourseByAuthorId(Long id);
}
