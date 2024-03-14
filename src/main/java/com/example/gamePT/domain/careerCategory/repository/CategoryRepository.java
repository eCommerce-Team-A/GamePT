package com.example.gamePT.domain.careerCategory.repository;

import com.example.gamePT.domain.careerCategory.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
