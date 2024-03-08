package com.example.gamePT.domain.order.repository;

import com.example.gamePT.domain.order.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    @Query("select "
            + "distinct e "
            + "from OrderEntity e "
            + "where "
            + "   e.siteUser.id = :id ")
    Page<OrderEntity> findAllBySiteUser(@Param("id") Long id, Pageable pageable);
}
