package com.example.backend.controllers;

import com.example.backend.entities.Brand;
import com.example.backend.repositories.BrandRepository;
import com.example.backend.services.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	Brand getBrand(@PathVariable Long brandId) {
		return brandRepository.findBrandById(brandId);
	}
	
	//TODO: create brand
	@PostMapping("/brands")
	ResponseEntity<String> createBrand(@RequestBody Brand brand) {
		if(brandService.findBrandByNameBrand(brand.getNameBrand()) != null) {
			return ResponseEntity.badRequest().body("Name Brand is required");
		}
		brandService.createBrand(brand);
		return ResponseEntity.ok("Create Brand Successfully");
	}
	
	//TODO: update brand
	@PutMapping("/brands")
	ResponseEntity<String> updateBrand(@RequestBody Brand brand) {
		if(brandService.findBrandById(brand.getId()) == null) {
			return ResponseEntity.badRequest().body("Id Brand is not existed");
		}
		brandService.updateBrand(brand);
		return ResponseEntity.ok("Update Brand Successfully");
	}
	
	//TODO: delete brand by id
	@DeleteMapping("/brands/{brandId}")
	ResponseEntity<String> deleteBrand(@PathVariable Long brandId) {
		if(brandService.findBrandById(brandId) == null) {
			return ResponseEntity.badRequest().body("Id Brand is not existed");
		}
		brandService.deleteBrandById(brandId);
		return ResponseEntity.ok("Delete Brand Successfully");
	}
}
