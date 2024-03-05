package com.example.gamePT.domain.cartItem.controller;

import com.example.gamePT.domain.cartItem.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartItemService cartItemService;
}
