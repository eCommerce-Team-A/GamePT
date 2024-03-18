package com.example.gamePT.domain.careerCategory.service;

import com.example.gamePT.domain.careerCategory.entity.Category;
import com.example.gamePT.domain.careerCategory.repository.CategoryRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void createCategory(String categoryName) {
        String icon = this.getIcon(categoryName);
        String color = this.getColor(categoryName);
        Category category = Category.builder()
                .category(categoryName)
                .icon(icon)
                .color(color)
                .build();
        this.categoryRepository.save(category);
    }

    public String getIcon(String categoryName) {
        return switch (categoryName) {
            case "프로게이머" -> "headset";
            case "프로팀코치" -> "chalkboard-user";
            case "스트리머" -> "youtube";
            case "대회입상" -> "trophy";
            case "최고티어" -> "star";
            default -> "ellipsis";
        };
    }

    public String getColor(String categoryName) {
        return switch (categoryName) {
            case "프로게이머" -> "darkkhaki";
            case "프로팀코치" -> "brown";
            case "스트리머" -> "red";
            case "대회입상", "최고티어" -> "gold";
            default -> "mediumseagreen;";
        };
    }

    public List<Category> getCategoryList() {
        return this.categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        Optional<Category> category = this.categoryRepository.findById(id);
        if (category.isEmpty()) {
            return null;
        }
        return category.get();
    }

    public void deleteCategory(Category category) {
        this.categoryRepository.delete(category);
    }
}
