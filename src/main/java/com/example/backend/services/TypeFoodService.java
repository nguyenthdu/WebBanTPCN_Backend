package com.example.backend.services;

import com.example.backend.entities.TypeFood;
import org.springframework.data.domain.Page;

public interface TypeFoodService {
	TypeFood createTypeFood(TypeFood typeFood);
	
	TypeFood findTypeFoodById(Long typeFoodId);
//	TypeFood findTypeFoodByNameTypeFood(String nameTypeFood);
	
	void deleteTypeFoodById(Long typeFoodId);
	
	TypeFood updateTypeFood(TypeFood typeFood);
	//Get all categories by type food
	
	Page<TypeFood> getTypeFoods(int pageNumber, int pageSize);
}
