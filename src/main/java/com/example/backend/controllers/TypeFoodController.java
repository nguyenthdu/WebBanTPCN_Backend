package com.example.backend.controllers;

import com.example.backend.entities.Category;
import com.example.backend.entities.TypeFood;
import com.example.backend.repositories.TypeFoodRepository;
import com.example.backend.services.TypeFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class TypeFoodController {
	@Autowired//tự động tìm kiếm và tiêm vào đối tượng
	private TypeFoodRepository typeFoodRepository;
	private final TypeFoodService typeFoodService;
	
	//TODO: get all uses
	@GetMapping("/typeFoods")
	List<TypeFood> getAllTypeFood() {
		return typeFoodRepository.findAll();
	}
	
	//TODO: create typeFood
	@PostMapping("/typeFoods")
	ResponseEntity<String> createTypeFood(@RequestBody TypeFood typeFood) {
		if(typeFoodService.findTypeFoodByNameTypeFood(typeFood.getName()) != null) {
			return ResponseEntity.badRequest().body("Name TypeFood is required");
		}
		typeFoodService.createTypeFood(typeFood);
		return ResponseEntity.ok("Create TypeFood Successfully");
	}
	
	//TODO: get typeFood by id
	@GetMapping("/typeFoods/{typeFoodId}")
	TypeFood getTypeFoodById(@PathVariable Long typeFoodId) {
		return typeFoodRepository.findTypeFoodById(typeFoodId);
	}
	
	//TODO:  get all list category by type food
	@GetMapping("/typeFoods/categories/{typeFoodId}")
	ResponseEntity<Set<Category>> getAllCategoriesByTypeFood(@PathVariable Long typeFoodId) {
		TypeFood typeFood = typeFoodService.findTypeFoodById(typeFoodId);
		if(typeFood == null) {
			return ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok(typeFoodService.getAllCategoriesByTypeFood(typeFood));
	}
	
	//TODO: update typeFood
	@PutMapping("/typeFoods")
	ResponseEntity<String> updateTypeFood(@RequestBody TypeFood typeFood) {
		if(typeFoodService.findTypeFoodById(typeFood.getId()) == null) {
			return ResponseEntity.badRequest().body("Id TypeFood is not existed");
		}
		typeFoodService.updateTypeFood(typeFood);
		return ResponseEntity.ok("Update TypeFood Successfully");
	}
	
	//TODO: delete typeFood by id
	@DeleteMapping("/typeFoods/{typeFoodId}")
	ResponseEntity<String> deleteTypeFoodById(@PathVariable Long typeFoodId) {
		if(typeFoodService.findTypeFoodById(typeFoodId) == null) {
			return ResponseEntity.badRequest().body("Id TypeFood is not existed");
		}
		typeFoodService.deleteTypeFoodById(typeFoodId);
		return ResponseEntity.ok("Delete TypeFood Successfully");
	}
}
