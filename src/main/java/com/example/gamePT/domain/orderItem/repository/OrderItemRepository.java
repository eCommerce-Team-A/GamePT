package com.example.gamePT.domain.orderItem.repository;

import com.example.gamePT.domain.orderItem.entity.OrderItem;
import com.example.gamePT.domain.user.entity.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByBuyerIdAndCourseId(Long siteUserId, Long courseId);

    @Query("select oi from OrderItem oi " +
            "left join Course c " +
            "on oi.course = c " +
            "where c.author = :author " +
            "ORDER BY oi.createDate DESC" )
    Page<OrderItem> findByAuthor(@Param("author")SiteUser author, Pageable pageable);
}
