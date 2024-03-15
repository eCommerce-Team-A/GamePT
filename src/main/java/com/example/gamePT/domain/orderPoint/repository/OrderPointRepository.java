package com.example.gamePT.domain.orderPoint.repository;

import com.example.gamePT.domain.orderPoint.entity.OrderPoint;
import com.example.gamePT.domain.user.entity.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPointRepository extends JpaRepository<OrderPoint, Long> {
    Page<OrderPoint> findAllBySiteUser(SiteUser su, Pageable pageable);
}
