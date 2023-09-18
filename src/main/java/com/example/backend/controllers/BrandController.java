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

    //TODO: get brand by name brand
    @GetMapping("/getBrandByNameBrand/{nameBrand}")
    Brand getBrandByNameBrand(@PathVariable String nameBrand) {
        return brandRepository.findBrandByNameBrand(nameBrand);
    }

    //TODO: get brand by id
    @GetMapping("/getBrandById/{idBrand}")
    Brand getBrandById(@PathVariable Long idBrand) {
        return brandRepository.findBrandById(idBrand);
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


    //TODO: update brand
    @PutMapping("/updateBrand")
    ResponseEntity<String> updateBrand(@RequestBody Brand brand) {
        if (brandService.findBrandById(brand.getId()) == null) {
            return ResponseEntity.badRequest().body("Id Brand is not existed");
        }
        brandService.updateBrand(brand);
        return ResponseEntity.ok("Update Brand Successfully");
    }

    //TODO: delete brand by id
    @DeleteMapping("/deleteBrandById/{idBrand}")
    ResponseEntity<String> deleteBrandById(@PathVariable Long idBrand) {
        if (brandService.findBrandById(idBrand) == null) {
            return ResponseEntity.badRequest().body("Id Brand is not existed");
        }
        brandService.deleteBrandById(idBrand);
        return ResponseEntity.ok("Delete Brand Successfully");
    }

}
