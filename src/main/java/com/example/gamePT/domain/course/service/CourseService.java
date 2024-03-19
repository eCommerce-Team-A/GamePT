package com.example.gamePT.domain.course.service;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.course.repository.CourseRepository;
import com.example.gamePT.domain.image.service.ImageService;
import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ImageService imageService;

    public List<Course> getCourseForMain() {

        return courseRepository.findTop4ByOrderByCreateDateDesc();

    }


    public Course createCourse(SiteUser author, String name, String introduce, String curriculum, Integer price, int discountRate) {
        Course course = Course.builder()
                .author(author)
                .name(name)
                .introduce(introduce)
                .curriculum(curriculum)
                .price(price)
                .discountRate(discountRate)
                .isActive(true)
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

    public Page<Course> findAll(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 8, Sort.by(sorts));

        return this.courseRepository.findAll(pageable);
    }

    public Page<Course> findAllByKeyword(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page, 8, Sort.by(sorts));

        return this.courseRepository.findAllByKeyword(pageable, kw);
    }

    public List<Course> findCourseByAuthorId(Long id) {
        return this.courseRepository.findCourseByAuthorId(id);
    }

    public Course updateCourse(Long id, String name, String introduce, String curriculum, Integer price, int discountRate
            , MultipartFile introduceImg, MultipartFile curriculumImg) throws IOException {
        Course _course = findCourseById(id);
        Course course = _course.toBuilder()
                .name(name)
                .introduce(introduce)
                .curriculum(curriculum)
                .price(price)
                .discountRate(discountRate)
                .isActive(true)
                .build();
        this.courseRepository.save(course);
        if (!introduceImg.isEmpty()){
            this.imageService.updateCourseIntroduce(course, introduceImg);
        }
        if (!curriculumImg.isEmpty()) {
            this.imageService.updateCourseCurriculum(course, curriculumImg);
        }
        return course;
    }

    public void disalbeCourse(Long id) {
        Course _course = findCourseById(id);
        Course course = _course.toBuilder()
                .isActive(false)
                .build();
        this.courseRepository.save(course);
    }

    public void activeCourse(Long id) {
        Course _course = findCourseById(id);
        Course course = _course.toBuilder()
                .isActive(true)
                .build();
        this.courseRepository.save(course);
    }
}
