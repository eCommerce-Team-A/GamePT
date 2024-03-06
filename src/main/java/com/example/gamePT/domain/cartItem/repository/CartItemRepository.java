package com.example.gamePT.domain.cartItem.repository;

import com.example.gamePT.domain.cartItem.entity.CartItem;
import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.user.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> findByBuyerAndCourse(SiteUser buyer, Course course);

    List<CartItem> findByBuyerId(Long id);
}
