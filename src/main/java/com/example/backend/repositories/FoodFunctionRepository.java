package com.example.backend.repositories;

import com.example.backend.entities.Brand;
import com.example.backend.entities.Category;
import com.example.backend.entities.FoodFunction;
import com.example.backend.entities.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodFunctionRepository extends JpaRepository<FoodFunction, Long>,
		PagingAndSortingRepository<FoodFunction, Long> {
	FoodFunction findFoodFunctionByNameFood(String nameFoodFunction);
	
	FoodFunction findFoodFunctionByCode(String code);
	
	List<FoodFunction> findByBrand(Brand brand);
	
	List<FoodFunction> findByCategory(Category category);
	
	List<FoodFunction> findByManufacturer(Manufacturer manufacturer);
}
