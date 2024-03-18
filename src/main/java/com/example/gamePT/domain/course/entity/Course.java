package com.example.gamePT.domain.course.entity;

import com.example.gamePT.domain.review.entity.Review;
import com.example.gamePT.domain.user.entity.SiteUser;
import com.example.gamePT.global.jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Course extends BaseEntity {
    @ManyToOne
    private SiteUser author;
    @OneToMany(mappedBy = "course",cascade = CascadeType.REMOVE)
    private List<Review> reviewList;
    private String name;
    private String introduce;
    private String curriculum;
    private Integer price;
    private int discountRate;
    private boolean isActive;
}
