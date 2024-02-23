package com.example.gamePT.domain.course.service;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public Course createCourse(String expertname, String gameCategoryname, String name, String information, Integer price) {
        Course course = Course.builder()
                .expertname(expertname)
                .gameCategoryname(gameCategoryname)
                .name(name)
                .information(information)
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

    public List<Course> findAllCourse(){
        return this.courseRepository.findAll();
    }
}
