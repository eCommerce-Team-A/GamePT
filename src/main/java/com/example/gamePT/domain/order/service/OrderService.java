package com.example.gamePT.domain.order.service;

import com.example.gamePT.domain.order.entity.OrderEntity;
import com.example.gamePT.domain.order.repository.OrderRepository;
import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderEntity create(SiteUser buyUser, Integer totalPrice) {
        OrderEntity orderEntity = OrderEntity.builder().siteUser(buyUser).totalPrice(totalPrice).build();
        return orderRepository.save(orderEntity);
    }
}
