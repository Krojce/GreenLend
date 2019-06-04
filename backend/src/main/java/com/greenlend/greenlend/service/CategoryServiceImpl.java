package com.greenlend.greenlend.service;

import com.greenlend.greenlend.exception.NotFoundException;
import com.greenlend.greenlend.exception.WrongValueException;
import com.greenlend.greenlend.model.Category;
import com.greenlend.greenlend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category findCategoryById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new NotFoundException("Category with id " + id + " has not been found.");
        }
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        if (category.getName() == null || category.getName().equals("")) {
            throw new WrongValueException("Category must have a name set!");
        }
        return categoryRepository.save(category);
    }
}
