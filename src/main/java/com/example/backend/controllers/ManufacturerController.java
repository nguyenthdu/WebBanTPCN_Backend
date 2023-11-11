package com.example.backend.controllers;

import com.example.backend.entities.FoodFunction;
import com.example.backend.entities.Manufacturer;
import com.example.backend.repositories.ManufacturerRepository;
import com.example.backend.services.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class ManufacturerController {
	@Autowired
	private ManufacturerRepository manufacturerRepository;
	private final ManufacturerService manufacturerService;
	
	//TODO: get all manufacturers
	@GetMapping("/manufacturers")
	List<Manufacturer> getAllManufacturers() {
		return manufacturerRepository.findAll();
	}
	
	//TODO: create manufacturer
	@PostMapping("/manufacturers")
	ResponseEntity<String> createManufacturer(@RequestBody Manufacturer manufacturer) {
		if(manufacturerService.findManufacturerByNameManufacturer(manufacturer.getNameManufacturer()) != null) {
			return ResponseEntity.badRequest().body("Name Manufacturer is required");
		}
		manufacturerService.createManufacturer(manufacturer);
		return ResponseEntity.ok("Create Manufacturer Successfully");
	}
	
	//TODO: get Manufacturer by id
	@GetMapping("/manufacturers/{manufacturerId}")
	Manufacturer getManufacturer(@PathVariable Long manufacturerId) {
		return manufacturerRepository.findManufacturerById(manufacturerId);
	}
	//TODO: get Manufacturer by Manufacturer name
//	@GetMapping("/manufacturers/{nameManufacturer}")
//	Manufacturer findManufacturerByManufacturerName(@PathVariable String nameManufacturer) {
//		return manufacturerRepository.findManufacturerByNameManufacturer(nameManufacturer);
//	}
	
	//TODO: update Manufacturer
	@PutMapping("/manufacturers")
	ResponseEntity<String> updateManufacturer(@RequestBody Manufacturer manufacturer) {
		if(manufacturerService.findManufacturerById(manufacturer.getId()) == null) {
			return ResponseEntity.badRequest().body("Id Manufacturer is not existed");
		}
		manufacturerService.updateManufacturer(manufacturer);
		return ResponseEntity.ok("Update Manufacturer Successfully");
	}
	
	//TODO: delete Manufacturer by id
	@DeleteMapping("/manufacturers/{manufacturerId}")
	ResponseEntity<String> deleteManufacturer(@PathVariable Long manufacturerId) {
		if(manufacturerService.findManufacturerById(manufacturerId) == null) {
			return ResponseEntity.badRequest().body("Id Manufacturer is not existed");
		}
		manufacturerService.deleteManufacturerById(manufacturerId);
		return ResponseEntity.ok("Delete Manufacturer Successfully");
	}
	
	//TODO: get all food functions by manufacturer
	@GetMapping("/manufacturers/{manufacturerId}/foodFunctions")
	ResponseEntity<Set<FoodFunction>> getAllFoodFunctionsByManufacturer(@PathVariable Long manufacturerId) {
		Manufacturer manufacturer = manufacturerService.findManufacturerById(manufacturerId);
		if(manufacturer == null) {
			return ResponseEntity.badRequest().body(null);
		}
		return ResponseEntity.ok(manufacturerService.getAllFoodFunctionByManufacturer(manufacturer));
	}
}
