package com.example.backend.controllers;

import com.example.backend.dtos.ErrorDto;
import com.example.backend.entities.Category;
import com.example.backend.entities.TypeFood;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.CategoryRepository;
import com.example.backend.services.CategoryService;
import com.example.backend.services.TypeFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/")
public class CategoryController {
	@Autowired
	private CategoryRepository categoryRepository;
	private final CategoryService categoryService;
	private final TypeFoodService typeFoodService;
	
	//TODO: get all categories
	@GetMapping("/categories")
	List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}
	
	//TODO: create category
	@PostMapping("/categories")
	ResponseEntity<ErrorDto> createCategory(@RequestParam("categoryName") String categoryName,
	                                        @RequestParam("typeFoodId") Long typeFoodId) {
		Category category = new Category();
		TypeFood typeFood = typeFoodService.findTypeFoodById(typeFoodId);
		try {
			category.setName(categoryName);
			category.setTypeFood(typeFood);
			categoryService.createCategory(category);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Create Category Successfully with id: " + category.getId(), HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	//TODO: get category by id
	@GetMapping("/categories/{categoryId}")
	ResponseEntity<Category> getCategory(@PathVariable Long categoryId) {
		Category category = categoryService.findCategoryById(categoryId);
		return ResponseEntity.ok().body(category);
	}
	
	//TODO: update category
	@PutMapping("/categories")
	ResponseEntity<ErrorDto> updateCategory(@RequestParam("categoryId") Long categoryId,
	                                        @RequestParam("categoryName") String categoryName,
	                                        @RequestParam("typeFoodId") Long typeFoodId) {
		Category category = categoryService.findCategoryById(categoryId);
		try {
			category.setName(categoryName);
			category.setTypeFood(typeFoodService.findTypeFoodById(typeFoodId));
			categoryService.updateCategory(category);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Update Category Successfully with id: " + category.getId(), HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	//TODO: delete category by id
	@DeleteMapping("/categories/{categoryId}")
	ResponseEntity<ErrorDto> deleteCategory(@PathVariable Long categoryId) {
		Category category = categoryService.findCategoryById(categoryId);
		try {
			categoryService.deleteCategoryById(categoryId);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Delete Category Successfully with id: " + category.getId(), HttpStatus.OK.value(), Instant.now().toString()));
	}
}
