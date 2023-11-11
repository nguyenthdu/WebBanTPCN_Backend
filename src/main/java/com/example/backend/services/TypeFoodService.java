package com.example.backend.services;

import com.example.backend.entities.Category;
import com.example.backend.entities.TypeFood;

import java.util.Set;

public interface TypeFoodService {
	TypeFood createTypeFood(TypeFood typeFood);
	
	TypeFood findTypeFoodById(Long typeFoodId);
	
	TypeFood findTypeFoodByNameTypeFood(String nameTypeFood);
	
	void deleteTypeFoodById(Long typeFoodId);
	
	TypeFood updateTypeFood(TypeFood typeFood);
	
	//Get all categories by type food
	Set<Category> getAllCategoriesByTypeFood(TypeFood typeFood);
}
