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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class FoodFunctionController {
	@Autowired
	private FoodFunctionRepository foodFunctionRepository;
	private final FoodFunctionService foodFunctionService;
	private final CategoryService categoryService;
	private final BrandService brandService;
	private final ManufacturerService manufacturerService;
	
	//TODO: get all food functions
	@GetMapping("/foodFunctions")
	List<FoodFunction> getAllFoodFunctions() {
		return foodFunctionRepository.findAll();
	}
	//TODO: create food function
	
	@PostMapping("/foodFunctions")
	public ResponseEntity<String> createFoodFunction(
			@RequestParam("nameFood") String nameFood,
			@RequestParam("description") String description,
			@RequestParam("price") double price,
			@RequestParam("images") MultipartFile images,
			@RequestParam("ingredients") String ingredients,
			@RequestParam("packingWay") String packingWay,//			@RequestParam("userObjectId") Long userObjectId,
			@RequestParam("dosageForm") String dosageForm,
			@RequestParam("placeOfManufacture") String placeOfManufacture,
			@RequestParam("expiryDate") int expiryDate,
			@RequestParam("manufacturerId") Long manufacturerId,
			@RequestParam("brandId") Long brandId,
			@RequestParam("categoryId") Long categoryId
	) throws IOException {
		FoodFunction foodFunction = new FoodFunction();
		foodFunction.setNameFood(nameFood);
		if(foodFunctionService.findFoodFunctionByNameFood(foodFunction.getNameFood()) != null) {
			return ResponseEntity.badRequest().body("Name Food Function is required");
		}
		Manufacturer manufacturer = manufacturerService.findManufacturerById(manufacturerId);
		Brand brand = brandService.findBrandById(brandId);
		Category category = categoryService.findCategoryById(categoryId);
		foodFunction.setDescription(description);
		foodFunction.setPrice(price);
		foodFunction.setImages(images.getBytes());
		foodFunction.setIngredients(ingredients);
		foodFunction.setPackingWay(packingWay);
		foodFunction.setDosageForm(dosageForm);
		foodFunction.setPlaceOfManufacture(placeOfManufacture);
		foodFunction.setExpiryDate(expiryDate);
		foodFunction.setManufacturer(manufacturer);
		foodFunction.setBrand(brand);
		foodFunction.setCategory(category);
		foodFunctionService.createFoodFunction(foodFunction);
		return ResponseEntity.ok("Create Food Function Successfully");
	}
	
	//TODO: get food function by id
	@GetMapping("/foodFunctions/{id}")
	ResponseEntity<FoodFunction> getFoodFunctionById(@PathVariable(value = "id") Long foodFunctionId) {
		FoodFunction foodFunction = foodFunctionService.findFoodFunctionById(foodFunctionId);
		if(foodFunction == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(foodFunction);
	}
	
	//TODO: update food function by id
	@PutMapping("/foodFunctions")
	ResponseEntity<String> updateFoodFunction(
			@RequestParam("id") Long id,
			@RequestParam("nameFood") String nameFood,
			@RequestParam("description") String description,
			@RequestParam("price") double price,
			@RequestParam("images") MultipartFile images,
			@RequestParam("ingredients") String ingredients,
			@RequestParam("packingWay") String packingWay,//			@RequestParam("userObjectId") Long userObjectId,
			@RequestParam("dosageForm") String dosageForm,
			@RequestParam("placeOfManufacture") String placeOfManufacture,
			@RequestParam("expiryDate") int expiryDate,
			@RequestParam("manufacturerId") Long manufacturerId,
			@RequestParam("brandId") Long brandId,
			@RequestParam("categoryId") Long categoryId,
			@RequestParam("status") boolean status,
			@RequestParam("discount") int discount
	) throws IOException {
		FoodFunction foodFunction = foodFunctionService.findFoodFunctionById(id);
		if(foodFunction == null) {
			return ResponseEntity.notFound().build();
		}
		Manufacturer manufacturer = manufacturerService.findManufacturerById(manufacturerId);
		Brand brand = brandService.findBrandById(brandId);
		Category category = categoryService.findCategoryById(categoryId);
		foodFunction.setNameFood(nameFood);
		foodFunction.setDescription(description);
		foodFunction.setPrice(price);
		foodFunction.setImages(images.getBytes());
		foodFunction.setIngredients(ingredients);
		foodFunction.setPackingWay(packingWay);
		foodFunction.setDosageForm(dosageForm);
		foodFunction.setPlaceOfManufacture(placeOfManufacture);
		foodFunction.setExpiryDate(expiryDate);
		foodFunction.setStatus(status);
		foodFunction.setDiscount(discount);
		foodFunction.setManufacturer(manufacturer);
		foodFunction.setBrand(brand);
		foodFunction.setCategory(category);
		foodFunctionService.updateFoodFunction(foodFunction);
		return ResponseEntity.ok("Update Food Function Successfully");
	}
	
	//TODO: delete food function by id
	@DeleteMapping("/foodFunctions/{foodFunctionId}")
	ResponseEntity<String> deleteFoodFunction(@PathVariable Long foodFunctionId) {
		if(foodFunctionService.findFoodFunctionById(foodFunctionId) == null) {
			return ResponseEntity.badRequest().body("Id Food Function is not existed");
		}
		foodFunctionService.deleteFoodFunctionById(foodFunctionId);
		return ResponseEntity.ok("Delete Food Function Successfully");
	}
	
	//TODO: get all food functions by page
	@GetMapping("/foodFunctions/page/{pageNumber}")
	ResponseEntity<List<FoodFunction>> getAllFoodFunctionsByPage(@PathVariable(value = "pageNumber") int pageNumber) {
		List<FoodFunction> foodFunctions = foodFunctionService.getAllFoodFunction(pageNumber, 2);
		if(foodFunctions == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(foodFunctions);
	}
}
