package com.example.backend.services.impl;

import com.example.backend.entities.Category;
import com.example.backend.entities.FoodFunction;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.CategoryRepository;
import com.example.backend.services.CategoryService;
import org.springframework.http.HttpStatus;
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
		if(categoryRepository.findCategoryByName(category.getName()) != null) {
			throw new AppException("Name Category is existed", HttpStatus.BAD_REQUEST);
		}
		return categoryRepository.save(category);
	}
	
	@Override
	public Category findCategoryById(Long categoryId) {
		return categoryRepository.findById(categoryId).orElseThrow(() -> new AppException("Id Category is not existed with id: " + categoryId, HttpStatus.NOT_FOUND));
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
