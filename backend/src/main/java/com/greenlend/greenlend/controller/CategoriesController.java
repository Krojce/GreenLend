package com.greenlend.greenlend.controller;

import com.greenlend.greenlend.model.Category;
import com.greenlend.greenlend.service.CategoryService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class CategoriesController {
    private final CategoryService categoryService;

    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public List<Category> findAllCategories() {
        return categoryService.findAllCategories();
    }

    @GetMapping("/categories/{id}")
    public Category findCategoryByiId(@PathVariable Long id) {
        return categoryService.findCategoryById(id);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }
}
