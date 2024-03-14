package com.example.gamePT.domain.orderPoint.repository;

import com.example.gamePT.domain.orderPoint.entity.OrderPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderPointRepository extends JpaRepository<OrderPoint, Long> {
}
