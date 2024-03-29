package com.example.gamePT.domain.orderItem.entity;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.order.entity.OrderEntity;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity {

    @ManyToOne
    private OrderEntity orderEntity;

    @ManyToOne
    private SiteUser buyer;

    @ManyToOne
    private Course course;

    private String name;

    private Integer price;
}
