package com.example.backend.services;

import com.example.backend.dtos.FoodFunctionDto;
import com.example.backend.entities.FoodFunction;
import com.example.backend.entities.ImageFile;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FoodFunctionService {
	FoodFunction createFoodFunction(FoodFunction foodFunction);
	
	FoodFunction findFoodFunctionByNameFood(String nameFoodFunction);
	
	FoodFunction findFoodFunctionById(Long idFoodFunction);
	
	FoodFunctionDto findById(Long foodFunctionId);
	
	void deleteFoodFunctionById(Long foodFunctionId);
	
	FoodFunction updateFoodFunction(FoodFunction foodFunction);
	
	Page<FoodFunction> getAllFoodFunction(int pageNumber, int pageSize);
	
	//get all image of food function
	List<ImageFile> getAllImageFoodFunction(Long idFoodFunction);
}
