package com.example.backend.controllers;

import com.example.backend.dtos.ErrorDto;
import com.example.backend.entities.FoodFunction;
import com.example.backend.entities.Manufacturer;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.ManufacturerRepository;
import com.example.backend.services.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
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
	ResponseEntity<ErrorDto> createManufacturer(@RequestBody Manufacturer manufacturer) {
		try {
			manufacturerService.createManufacturer(manufacturer);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Create Manufacturer Successfully with manufacturer id: " + manufacturer.getId(), HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	//TODO: get Manufacturer by id
	@GetMapping("/manufacturers/{manufacturerId}")
	ResponseEntity<Manufacturer> getManufacturer(@PathVariable Long manufacturerId) {
		Manufacturer manufacturer = manufacturerService.findManufacturerById(manufacturerId);
		return ResponseEntity.ok().body(manufacturer);
	}
	//TODO: get Manufacturer by Manufacturer name
//	@GetMapping("/manufacturers/{nameManufacturer}")
//	Manufacturer findManufacturerByManufacturerName(@PathVariable String nameManufacturer) {
//		return manufacturerRepository.findManufacturerByNameManufacturer(nameManufacturer);
//	}
	
	//TODO: update Manufacturer
	@PutMapping("/manufacturers")
	ResponseEntity<ErrorDto> updateManufacturer(@RequestBody Manufacturer manufacturer) {
		Manufacturer manufacturer1 = manufacturerService.findManufacturerById(manufacturer.getId());
		try {
			manufacturerService.updateManufacturer(manufacturer);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Update Manufacturer Successfully with manufacturer id: " + manufacturer.getId(), HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	//TODO: delete Manufacturer by id
	@DeleteMapping("/manufacturers/{manufacturerId}")
	ResponseEntity<ErrorDto> deleteManufacturer(@PathVariable Long manufacturerId) {
		manufacturerService.deleteManufacturerById(manufacturerId);
		try {
			manufacturerService.deleteManufacturerById(manufacturerId);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Delete Manufacturer Successfully with manufacturer id: " + manufacturerId, HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	//TODO: get all food functions by manufacturer
	@GetMapping("/manufacturers/{manufacturerId}/foodFunctions")
	ResponseEntity<Set<FoodFunction>> getAllFoodFunctionsByManufacturer(@PathVariable Long manufacturerId) {
		Manufacturer manufacturer = manufacturerService.findManufacturerById(manufacturerId);
		return ResponseEntity.ok(manufacturerService.getAllFoodFunctionByManufacturer(manufacturer));
	}
}
