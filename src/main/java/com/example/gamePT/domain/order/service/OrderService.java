package com.example.gamePT.domain.order.service;

import com.example.gamePT.domain.order.entity.OrderEntity;
import com.example.gamePT.domain.order.repository.OrderRepository;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderEntity create(SiteUser buyUser, Integer totalPrice) {
        OrderEntity orderEntity = OrderEntity.builder().siteUser(buyUser).totalPrice(totalPrice).build();
        return orderRepository.save(orderEntity);
    }

    public Page<OrderEntity> getList(int page, Long siteUserId) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sorts));
        return this.orderRepository.findAllBySiteUser(siteUserId, pageable);
    }

    public OrderEntity findById(Long orderId) {

        Optional<OrderEntity> oe = orderRepository.findById(orderId);

        if( oe.isEmpty()){
            return null;
        }

        if(!oe.get().getSiteUser().getUsername().equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            return null;
        }

        return oe.get();
    }

    public void checkAmount(String orderId, String amount) {
        //해당 주문번호의 최종 결제금액이 amount와 일치하는지 검증
        boolean matched = true;

        if (!matched) throw new RuntimeException("결제금액이 일치하지 않습니다.") ;
    }

    public void setPaymentComplete(String orderId) {

    }
}
