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
@RequestMapping("api/v1/brands")
public class BrandController {
    @Autowired
    private BrandRepository brandRepository;
    private final BrandService brandService;

    //TODO: get all brands
    @GetMapping("/getAllBrands")
    List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    //TODO: create brand
    @PostMapping("/createBrand")
    ResponseEntity<String> createBrand(@RequestBody Brand brand) {

        if (brandService.findBrandByNameBrand(brand.getNameBrand()) != null) {
            return ResponseEntity.badRequest().body("Name Brand is required");
        }
        brandService.createBrand(brand);
        return ResponseEntity.ok("Create Brand Successfully");
    }
}
