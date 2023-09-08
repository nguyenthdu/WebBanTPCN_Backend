package com.example.backend.services.impl;

import com.example.backend.entities.Category;
import com.example.backend.entities.FoodFunction;
import com.example.backend.repositories.CategoryRepository;
import com.example.backend.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category findCategoryBynameCategory(String nameCategory) {
        return categoryRepository.findCategoryBynameCategory(nameCategory);
    }

    @Override
    public Category findCategoryById(Long idCategory) {
        return categoryRepository.findCategoryById(idCategory);
    }

    @Override
    public Set<FoodFunction> getAllFoodFunctionByCategory(Category category) {
        return category.getFoodFunctions();
    }
}
