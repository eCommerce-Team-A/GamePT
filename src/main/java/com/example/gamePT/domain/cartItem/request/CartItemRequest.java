package com.example.gamePT.domain.cartItem.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class CartItemRequest {
    @Getter
    @Setter
    public static class AddCartRequest{
        private Long buyerId;
        private Long courseId;
    }
}
