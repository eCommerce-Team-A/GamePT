package com.example.gamePT.domain.course.service;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.repository.CourseRepository;
import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public Course createCourse(SiteUser author, String gameCategoryname, String name, String introduce, String curriculum, Integer price) {
        Course course = Course.builder()
                .author(author)
                .gameCategoryname(gameCategoryname)
                .name(name)
                .introduce(introduce)
                .curriculum(curriculum)
                .price(price)
                .build();
        this.courseRepository.save(course);

        return findCourseById(course.getId());
    }


    public Course findCourseById(Long id) {
        Optional<Course> _course = this.courseRepository.findById(id);
        if (_course.isEmpty()) {
            return null;
        }
        return _course.get();
    }

    public List<Course> findAllCourse() {
        return this.courseRepository.findAll();
    }

    public List<Course> findCourseByAuthorId(Long id) {
        return this.courseRepository.findCourseByAuthorId(id);
    }

    public Course updateCourse(Long id, String gameCategoryname, String name,
                             String introduce, String curriculum, Integer price) {
        Course _course = findCourseById(id);
        Course course = _course.toBuilder()
                .gameCategoryname(gameCategoryname)
                .name(name)
                .introduce(introduce)
                .curriculum(curriculum)
                .price(price)
                .isActive(true)
                .build();
        this.courseRepository.save(course);

        return course;
    }

    public void disalbeCourse(Long id) {
        Course _course = findCourseById(id);
        Course course = _course.toBuilder()
                .isActive(false)
                .build();
        this.courseRepository.save(course);
    }
}
