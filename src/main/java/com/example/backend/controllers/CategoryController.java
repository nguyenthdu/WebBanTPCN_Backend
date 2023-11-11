package com.example.backend.controllers;

import com.example.backend.entities.Category;
import com.example.backend.entities.FoodFunction;
import com.example.backend.repositories.CategoryRepository;
import com.example.backend.services.CategoryService;
import com.example.backend.services.TypeFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

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
	ResponseEntity<String> createCategory(@RequestParam("categoryName") String categoryName,
	                                      @RequestParam("typeFoodId") Long typeFoodId) {
		Category category = new Category();
		category.setName(categoryName);
		if(categoryService.findCategoryByName(category.getName()) != null) {
			return ResponseEntity.badRequest().body("Name Category is required");
		}
		category.setTypeFood(typeFoodService.findTypeFoodById(typeFoodId));
		categoryService.createCategory(category);
		return ResponseEntity.ok("Create Category Successfully");
	}
	
	//TODO: get category by id
	@GetMapping("/categories/{categoryId}")
	Category getCategory(@PathVariable Long categoryId) {
		return categoryRepository.findCategoryById(categoryId);
	}
	
	//TODO: Lấy ra danh sách các food function có trong 1 category
	@GetMapping("/categories/{categoryId}/foodFunction")
	ResponseEntity<Set<FoodFunction>> getAllFoodFunctionByCategory(@PathVariable Long categoryId) {
		Category category = categoryService.findCategoryById(categoryId);
		if(category == null) {
			return ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok(categoryService.getAllFoodFunctionByCategory(category));
	}
	
	//TODO: update category
	@PutMapping("/categories")
	ResponseEntity<String> updateCategory(@RequestParam("categoryId") Long categoryId,
	                                      @RequestParam("categoryName") String categoryName,
	                                      @RequestParam("typeFoodId") Long typeFoodId) {
		Category category = categoryService.findCategoryById(categoryId);
		if(category == null) {
			return ResponseEntity.badRequest().body("Id Category is not existed");
		}
		category.setName(categoryName);
		category.setTypeFood(typeFoodService.findTypeFoodById(typeFoodId));
		categoryService.updateCategory(category);
		return ResponseEntity.ok("Update Category Successfully");
	}
	
	//TODO: delete category by id
	@DeleteMapping("/categories/{categoryId}")
	ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
		if(categoryService.findCategoryById(categoryId) == null) {
			return ResponseEntity.badRequest().body("Id Category is not existed");
		}
		categoryService.deleteCategoryById(categoryId);
		return ResponseEntity.ok("Delete Category Successfully");
	}
}
