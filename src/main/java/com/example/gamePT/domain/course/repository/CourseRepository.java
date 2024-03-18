package com.example.gamePT.domain.course.repository;

import com.example.gamePT.domain.course.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findCourseByAuthorId(Long id);
    Page<Course> findAll(Pageable pageable);

    @Query("select "
            + " c "
            + "from Course c "
            + "where "
            + "   c.name like %:kw% "
            + "   or c.curriculum like %:kw% "
            + "   or c.introduce like %:kw% ")
    Page<Course> findAllByKeyword( Pageable pageable, @Param("kw") String kw);

    List<Course> findTop4ByOrderByCreateDateDesc();
}
