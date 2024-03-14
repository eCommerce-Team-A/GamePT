package com.example.gamePT.domain.orderPoint.servcie;

import com.example.gamePT.domain.orderPoint.entity.OrderPoint;
import com.example.gamePT.domain.orderPoint.repository.OrderPointRepository;
import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderPointService {
    private final OrderPointRepository orderPointRepository;

    public OrderPoint create(SiteUser siteUser,String orderId, int point){

        OrderPoint op = OrderPoint.builder()
                .siteUser(siteUser)
                .orderId(orderId)
                .point(point)
                .build();

        orderPointRepository.save(op);

        return op;
    }

    public OrderPoint findById(Long id){

        Optional<OrderPoint> op = orderPointRepository.findById(id);

        if(op.isEmpty()){
            return null;
        }

        return op.get();
    }

}
