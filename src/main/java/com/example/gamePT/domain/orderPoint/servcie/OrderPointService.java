package com.example.gamePT.domain.orderPoint.servcie;

import com.example.gamePT.domain.orderPoint.entity.OrderPoint;
import com.example.gamePT.domain.orderPoint.repository.OrderPointRepository;
import com.example.gamePT.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public Page<OrderPoint> getListForMyPage(int page, SiteUser su) {
        List<Sort.Order> sorts = new ArrayList<>();

        sorts.add(Sort.Order.desc("createDate"));

        Pageable pageable = PageRequest.of(page,5,Sort.by(sorts));

        return this.orderPointRepository.findAllBySiteUser(su,pageable);

    }

}
