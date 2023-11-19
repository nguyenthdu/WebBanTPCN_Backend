package com.example.backend.controllers;

import com.example.backend.dtos.ErrorDto;
import com.example.backend.entities.Brand;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.BrandRepository;
import com.example.backend.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class BrandController {
	@Autowired
	private BrandRepository brandRepository;
	private final BrandService brandService;
	
	//TODO: get all brands
	@GetMapping("/brands")
	List<Brand> getAllBrands() {
		return brandRepository.findAll();
	}
	
	//TODO: get brand by name brand
//	@GetMapping("/brands/{brandName}")
//	Brand getBrandByNameBrand(@PathVariable String brandName) {
//		return brandRepository.findBrandByNameBrand(brandName);
//	}
//
	//TODO: get brand by id
	@GetMapping("/brands/{brandId}")
	ResponseEntity<Brand> getBrandById(@PathVariable Long brandId) {
		Brand brand = brandService.findBrandById(brandId);
		return ResponseEntity.ok().body(brand);
	}
	
	//TODO: create brand
	@PostMapping("/brands")
	ResponseEntity<ErrorDto> createBrand(@RequestBody Brand brand) {
		try {
			brandService.createBrand(brand);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Create Brand Successfully with brand id: " + brand.getId(), HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	//TODO: update brand
	@PutMapping("/brands")
	ResponseEntity<ErrorDto> updateBrand(@RequestBody Brand brand) {
		Brand brand1 = brandService.findBrandById(brand.getId());
		try {
			brandService.updateBrand(brand);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Update Brand Successfully with brand id: " + brand1.getId(), HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	//TODO: delete brand by id
	@DeleteMapping("/brands/{brandId}")
	ResponseEntity<ErrorDto> deleteBrand(@PathVariable Long brandId) {
		Brand brand = brandService.findBrandById(brandId);
		try {
			brandService.deleteBrandById(brandId);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Delete Brand Successfully with brand id: " + brand.getId(), HttpStatus.OK.value(), Instant.now().toString()));
	}
}
