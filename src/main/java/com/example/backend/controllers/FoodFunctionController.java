package com.example.backend.controllers;

import com.example.backend.dtos.ErrorDto;
import com.example.backend.entities.Brand;
import com.example.backend.entities.Category;
import com.example.backend.entities.FoodFunction;
import com.example.backend.entities.Manufacturer;
import com.example.backend.exceptions.AppException;
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

import java.io.IOException;
import java.time.Instant;
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
	public ResponseEntity<ErrorDto> createFoodFunction(
			@RequestParam("nameFood") String nameFood,
			@RequestParam("description") String description,
			@RequestParam("price") double price,
			@RequestParam("quantity") int quantity,
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
		Manufacturer manufacturer = manufacturerService.findManufacturerById(manufacturerId);
		Brand brand = brandService.findBrandById(brandId);
		Category category = categoryService.findCategoryById(categoryId);
		try {
			foodFunction.setNameFood(nameFood);
			foodFunction.setDescription(description);
			foodFunction.setPrice(price);
			foodFunction.setQuantity(quantity);
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
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Create Food Function Successfully with id: " + foodFunction.getId(), HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	//TODO: get food function by id
	@GetMapping("/foodFunctions/{id}")
	ResponseEntity<FoodFunction> getFoodFunctionById(@PathVariable(value = "id") Long foodFunctionId) {
		FoodFunction foodFunction = foodFunctionService.findFoodFunctionById(foodFunctionId);
		return ResponseEntity.ok().body(foodFunction);
	}
	
	//TODO: get food by name
	@GetMapping("/foodFunctions/name/{name}")
	ResponseEntity<FoodFunction> getFoodFunctionByName(@PathVariable(value = "name") String nameFood) {
		FoodFunction foodFunction = foodFunctionService.findFoodFunctionByNameFood(nameFood);
		return ResponseEntity.ok().body(foodFunction);
	}
	
	//TODO: update food function by id
	@PutMapping("/foodFunctions")
	ResponseEntity<ErrorDto> updateFoodFunction(
			@RequestParam("id") Long id,
			@RequestParam("nameFood") String nameFood,
			@RequestParam("description") String description,
			@RequestParam("price") double price,
			@RequestParam("quantity") int quantity,
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
		Manufacturer manufacturer = manufacturerService.findManufacturerById(manufacturerId);
		Brand brand = brandService.findBrandById(brandId);
		Category category = categoryService.findCategoryById(categoryId);
		try {
			foodFunction.setNameFood(nameFood);
			foodFunction.setDescription(description);
			foodFunction.setPrice(price);
			foodFunction.setQuantity(quantity);
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
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Update Food Function Successfully with id: " + id, HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	//TODO: delete food function by id
	@DeleteMapping("/foodFunctions/{foodFunctionId}")
	ResponseEntity<ErrorDto> deleteFoodFunction(@PathVariable Long foodFunctionId) {
		FoodFunction foodFunction = foodFunctionService.findFoodFunctionById(foodFunctionId);
		try {
			foodFunctionService.deleteFoodFunctionById(foodFunctionId);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Delete Food Function Successfully with id: " + foodFunctionId, HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	//TODO: get all food functions by page
	@GetMapping("/foodFunctions/page/{pageNumber}")
	ResponseEntity<List<FoodFunction>> getAllFoodFunctionsByPage(@PathVariable(value = "pageNumber") int pageNumber) {
		List<FoodFunction> foodFunctions = foodFunctionService.getAllFoodFunction(pageNumber, 2);
		return ResponseEntity.ok().body(foodFunctions);
	}
}
