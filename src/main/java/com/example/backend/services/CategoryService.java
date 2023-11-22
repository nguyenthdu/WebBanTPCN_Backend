package com.example.backend.services;

import com.example.backend.entities.Category;

public interface CategoryService {
	Category createCategory(Category category);
	
	Category findCategoryById(Long idCategory);
	
	void deleteCategoryById(Long idCategory);
	
	Category updateCategory(Category category);
}
