package com.example.gamePT.domain.orderItem.repository;

import com.example.gamePT.domain.cartItem.entity.CartItem;
import com.example.gamePT.domain.orderItem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
