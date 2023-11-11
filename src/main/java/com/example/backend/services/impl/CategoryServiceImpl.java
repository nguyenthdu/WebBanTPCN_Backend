package com.example.backend.services.impl;

import com.example.backend.entities.Category;
import com.example.backend.entities.FoodFunction;
import com.example.backend.repositories.CategoryRepository;
import com.example.backend.services.CategoryService;
import org.springframework.stereotype.Service;

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
	public Category findCategoryByName(String categoryName) {
		return categoryRepository.findCategoryByName(categoryName);
	}
	
	@Override
	public Category findCategoryById(Long categoryId) {
		return categoryRepository.findCategoryById(categoryId);
	}
	
	@Override
	public Set<FoodFunction> getAllFoodFunctionByCategory(Category category) {
		return category.getFoodFunctions();
	}
	
	@Override
	public void deleteCategoryById(Long categoryId) {
		categoryRepository.deleteById(categoryId);
	}
	
	@Override
	public Category updateCategory(Category category) {
		return categoryRepository.save(category);
	}
}
