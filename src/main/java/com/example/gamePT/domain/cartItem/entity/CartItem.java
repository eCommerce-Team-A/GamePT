package com.example.gamePT.domain.cartItem.entity;

import com.example.gamePT.domain.course.entity.Course;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.mapping.ToOne;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends BaseEntity {

    @ManyToOne
    private SiteUser buyer;

    @ManyToOne
    private Course course;
}
