package com.example.gamePT.domain.tossPayments.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class TossPaymentsRequest {
    @Getter
    @Setter
    public static class Order {

        private int orderAmount;

    }
}
