package com.example.backend.services;

import com.example.backend.entities.Brand;
import com.example.backend.entities.Category;
import com.example.backend.entities.FoodFunction;

import java.util.List;
import java.util.Set;

public interface CategoryService {
    Category createCategory(Category category);
    Category findCategoryBynameCategory(String nameCategory);
    Category findCategoryById(Long idCategory);
    // in ra tất cả các food function có trong 1 category
    Set<FoodFunction> getAllFoodFunctionByCategory(Category category);

    void deleteCategoryById(Long idCategory);
    Category updateCategory(Category category);
}
