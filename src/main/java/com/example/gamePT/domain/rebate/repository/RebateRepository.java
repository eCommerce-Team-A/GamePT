package com.example.gamePT.domain.rebate.repository;

import com.example.gamePT.domain.rebate.entity.Rebate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RebateRepository extends JpaRepository<Rebate, Long> {
    @Query("SELECT c.author.id a1, " +
            "YEAR(oi.createDate) AS y1 , " +
            "MONTH(oi.createDate) AS m1, " +
            "SUM(oi.price) t1" +
            "FROM orderItem oi" +
            "INNER JOIN Course c ON " +
            "oi.course.id = c.id " +
            "GROUP BY " +
            "c.author.id, MONTH(oi.createDate) " +
            "HAVING " +
            "m1 = 3 AND c.author.id = 2;")
    List<Rebate> findByCourseAuthorId();
}
