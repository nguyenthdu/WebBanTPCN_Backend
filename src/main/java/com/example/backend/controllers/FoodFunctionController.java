package com.example.backend.controllers;

import com.example.backend.entities.Brand;
import com.example.backend.entities.Category;
import com.example.backend.entities.FoodFunction;
import com.example.backend.entities.Manufacturer;
import com.example.backend.repositories.FoodFunctionRepository;
import com.example.backend.services.BrandService;
import com.example.backend.services.CategoryService;
import com.example.backend.services.FoodFunctionService;
import com.example.backend.services.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

import java.util.*;


@RequiredArgsConstructor
@RestController

@RequestMapping("api/v1/foodFunctions")
public class FoodFunctionController {

    @Autowired
    private FoodFunctionRepository foodFunctionRepository;
    private final FoodFunctionService foodFunctionService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ManufacturerService manufacturerService;

    //TODO: get all food functions
    @GetMapping("/getAllFoodFunctions")
    List<FoodFunction> getAllFoodFunctions() {
        return foodFunctionRepository.findAll();
    }

    //TODO: create food function

    @PostMapping("/createFoodFunction")
    public ResponseEntity<String> createFoodFunction(
            @RequestParam("nameFood") String nameFood,
            @RequestParam("description") String description,
            @RequestParam("price") double price,
            @RequestParam("images") MultipartFile images,
            @RequestParam("ingredients") List<String> ingredients,
            @RequestParam("uses") List<String> uses,
            @RequestParam("packingWay") String packingWay,
            @RequestParam("userObject") List<String> userObject,
            @RequestParam("dosageForm") String dosageForm,
            @RequestParam("placeOfManufacture") String placeOfManufacture,
            @RequestParam("expiryDate") int expiryDate,
            @RequestParam("manufacturerId") Long manufacturerId,
            @RequestParam("brandId") Long brandId,
            @RequestParam("categoryId") Long categoryId
    ) throws IOException {
        FoodFunction foodFunction = new FoodFunction();
        foodFunction.setNameFood(nameFood);
        if (foodFunctionService.findFoodFunctionByNameFood(foodFunction.getNameFood()) != null) {
            return ResponseEntity.badRequest().body("Name Food Function is required");
        }
        foodFunction.setDescription(description);
        foodFunction.setPrice(price);
        byte[]image = images.getBytes();
        foodFunction.setImages(image);
        foodFunction.setIngredients(ingredients);
        foodFunction.setUses(uses);
        foodFunction.setPackingWay(packingWay);
        foodFunction.setUserObject(userObject);
        foodFunction.setDosageForm(dosageForm);
        foodFunction.setPlaceOfManufacture(placeOfManufacture);
        foodFunction.setExpiryDate(expiryDate);

        // Lấy thông tin Manufacturer, Brand và Category từ dữ liệu được gửi từ client
        Manufacturer manufacturer = manufacturerService.findManufacturerById(manufacturerId);
        Brand brand = brandService.findBrandById(brandId);
        Category category = categoryService.findCategoryById(categoryId);

        foodFunction.setManufacturer(manufacturer);
        foodFunction.setBrand(brand);
        foodFunction.setCategory(category);

        foodFunctionService.createFoodFunction(foodFunction);
        return ResponseEntity.ok("Create Food Function Successfully");


    }




}
