package com.example.gamePT.domain.order.entity;

import com.example.gamePT.domain.orderItem.entity.OrderItem;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity extends BaseEntity {

    @ManyToOne
    private SiteUser siteUser;

    private Integer totalPrice;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.REMOVE)
    @OrderBy("createDate desc")
    private List<OrderItem> orderItemList;

}
