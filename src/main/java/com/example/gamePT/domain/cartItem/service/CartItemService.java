package com.example.gamePT.domain.cartItem.service;

import com.example.gamePT.domain.cartItem.entity.CartItem;
import com.example.gamePT.domain.cartItem.repository.CartItemRepository;
import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItem findCartItem(SiteUser buyer, Course course) {
        Optional<CartItem> _cartItem = this.cartItemRepository.findByBuyerAndCourse(buyer, course);
        if (_cartItem.isEmpty()) {
            return null;
        }
        return _cartItem.get();
    }
}
