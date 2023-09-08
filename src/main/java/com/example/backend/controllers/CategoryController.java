package com.example.backend.controllers;

import com.example.backend.entities.Category;
import com.example.backend.entities.FoodFunction;
import com.example.backend.repositories.CategoryRepository;
import com.example.backend.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/categories")
public class CategoryController {
    @Autowired
private CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    //TODO: get all categories
    @GetMapping("/getAllCategories")
    List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    //TODO: create category
    @PostMapping("/createCategory")
    ResponseEntity<String> createCategory(@RequestBody  Category category){
        if(categoryService.findCategoryBynameCategory(category.getNameCategory())!=null){
            return ResponseEntity.badRequest().body("Name Category is required");
        }
        categoryService.createCategory(category);
        return ResponseEntity.ok("Create Category Successfully");
    }
//TODO: Lấy ra danh sách các food function có trong 1 category
    @GetMapping("/getAllFoodFunctionByCategory/{idCategory}")
    ResponseEntity<Set<FoodFunction>> getAllFoodFunctionByCategory(@PathVariable Long idCategory){
        Category category = categoryService.findCategoryById(idCategory);
        if(category==null){
            return ResponseEntity.badRequest().body(null);
        }

        return ResponseEntity.ok(categoryService.getAllFoodFunctionByCategory(category));
    }
}
