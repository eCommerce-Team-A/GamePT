package com.example.gamePT.domain.career.entity;

import com.example.gamePT.domain.careerCategory.entity.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CareerWithCategory {
    Career career;
    Category category;
    public CareerWithCategory(Career career, Category category) {
        this.career = career;
        this.category = category;
    }
}
