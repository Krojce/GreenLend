package com.greenlend.greenlend.service;

import com.greenlend.greenlend.model.Category;

import java.util.List;

public interface CategoryService {
    Category findCategoryById(Long id);

    List<Category> findAllCategories();

    Category addCategory(Category category);
}
