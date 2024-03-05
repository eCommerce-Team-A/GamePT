package com.example.gamePT.domain.cartItem.service;

import com.example.gamePT.domain.cartItem.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemService {
    private final CartItemRepository cartItemRepository;
}
