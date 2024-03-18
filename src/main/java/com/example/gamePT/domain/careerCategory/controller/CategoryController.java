package com.example.gamePT.domain.careerCategory.controller;

import com.example.gamePT.domain.careerCategory.entity.Category;
import com.example.gamePT.domain.careerCategory.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/create")
    public String createCategory(@RequestParam("category") String category) {
        this.categoryService.createCategory(category);
        return "redirect:/user/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id")Long id) {
        Category category = this.categoryService.getCategoryById(id);
        this.categoryService.deleteCategory(category);
        return "redirect:/user/admin";
    }
}
