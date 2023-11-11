package com.example.backend.repositories;

import com.example.backend.entities.FoodFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodFunctionRepository extends JpaRepository<FoodFunction, Long>,
		PagingAndSortingRepository<FoodFunction, Long> {
	FoodFunction findFoodFunctionByNameFood(String nameFoodFunction);
	
	FoodFunction findFoodFunctionById(Long idFoodFunction);
	
	FoodFunction findFoodFunctionByCode(String code);
}
