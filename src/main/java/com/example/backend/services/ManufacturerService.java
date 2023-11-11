package com.example.backend.services;

import com.example.backend.entities.FoodFunction;
import com.example.backend.entities.Manufacturer;

import java.util.Set;

public interface ManufacturerService {
	Manufacturer createManufacturer(Manufacturer manufacturer);
	
	Manufacturer findManufacturerByNameManufacturer(String nameManufacturer);
	
	Manufacturer findManufacturerById(Long idManufacturer);
	
	void deleteManufacturerById(Long idManufacturer);
	
	Manufacturer updateManufacturer(Manufacturer manufacturer);
	
	//find food by manufacturer
	Set<FoodFunction> getAllFoodFunctionByManufacturer(Manufacturer manufacturer);
}
