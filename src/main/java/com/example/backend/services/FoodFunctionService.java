package com.example.backend.services;

import com.example.backend.entities.FoodFunction;
import com.example.backend.entities.ImageFile;

import java.util.List;

public interface FoodFunctionService {
	FoodFunction createFoodFunction(FoodFunction foodFunction);
	
	FoodFunction findFoodFunctionByNameFood(String nameFoodFunction);
	
	FoodFunction findFoodFunctionById(Long idFoodFunction);
	
	void deleteFoodFunctionById(Long foodFunctionId);
	
	FoodFunction updateFoodFunction(FoodFunction foodFunction);
	
	List<FoodFunction> getAllFoodFunction(int pageNumber, int pageSize);
	
	//get all image of food function
	List<ImageFile> getAllImageFoodFunction(Long idFoodFunction);
}
