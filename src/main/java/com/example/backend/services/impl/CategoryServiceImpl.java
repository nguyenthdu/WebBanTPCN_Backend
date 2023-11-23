package com.example.backend.services.impl;

import com.example.backend.entities.Category;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.CategoryRepository;
import com.example.backend.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;
	
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
	public void deleteCategoryById(Long categoryId) {
		categoryRepository.deleteById(categoryId);
	}
	
	@Override
	public Category updateCategory(Category category) {
		return categoryRepository.save(category);
	}
	
	@Override
	public Page<Category> getCategories(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return categoryRepository.findAll(pageable);
	}
}
