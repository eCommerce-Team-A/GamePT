package com.example.gamePT.domain.cartItem.repository;

import com.example.gamePT.domain.cartItem.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
}
