package com.example.backend.controllers;

import com.example.backend.dtos.ErrorDto;
import com.example.backend.entities.*;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.FoodFunctionRepository;
import com.example.backend.services.BrandService;
import com.example.backend.services.CategoryService;
import com.example.backend.services.FoodFunctionService;
import com.example.backend.services.ManufacturerService;
import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
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
			@RequestParam("imageFiles") List<MultipartFile> imageFiles,
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
			//Xử lý lưu ảnh và giảm kích thước ảnh
			List<ImageFile> imageFileList = new ArrayList<>();
			for(MultipartFile file : imageFiles) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				Thumbnails.of(file.getInputStream())
						.size(372, 372)
						.keepAspectRatio(false)
						.outputFormat("jpg")
						.outputQuality(0.5)
						.toOutputStream(baos);
				byte[] resizedImageBytes = baos.toByteArray();
				ImageFile imageFile = new ImageFile();
				imageFile.setPicByte(resizedImageBytes);
				imageFileList.add(imageFile);
			}
			foodFunction.setImageFiles(imageFileList);
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
			@RequestParam("imageFiles") List<MultipartFile> imageFiles,
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
			// Xử lý danh sách ảnh mới
			List<ImageFile> newImageFileList = new ArrayList<>();
			for(MultipartFile file : imageFiles) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				Thumbnails.of(file.getInputStream())
						.size(372, 372)
						.keepAspectRatio(false)
						.outputFormat("jpg")
						.outputQuality(0.5)
						.toOutputStream(baos);
				byte[] resizedImageBytes = baos.toByteArray();
				ImageFile imageFile = new ImageFile();
				imageFile.setPicByte(resizedImageBytes);
				newImageFileList.add(imageFile);
			}
			// Xóa ảnh cũ và thêm ảnh mới
			foodFunction.getImageFiles().clear();
			foodFunction.getImageFiles().addAll(newImageFileList);
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
	public ResponseEntity<PageResponse<FoodFunction>> getAllFoodFunctionsByPage(@PathVariable(value = "pageNumber") int pageNumber) {
		int pageSize = 2; // Số lượng mục trên mỗi trang
		Page<FoodFunction> pageResult = foodFunctionService.getAllFoodFunction(pageNumber, pageSize);
		List<FoodFunction> foodFunctions = pageResult.getContent();
		PageResponse<FoodFunction> pageResponse = new PageResponse<>();
		pageResponse.setContent(foodFunctions);
		pageResponse.setTotalPages(pageResult.getTotalPages());
		pageResponse.setTotalElements(pageResult.getTotalElements());
		pageResponse.setCurrentPage(pageNumber);
		pageResponse.setPageSize(pageSize);
		return ResponseEntity.ok().body(pageResponse);
	}
//	@GetMapping("/foodFunctions/page/{pageNumber}")
//	ResponseEntity<List<FoodFunction>> getAllFoodFunctionsByPage(@PathVariable(value = "pageNumber") int pageNumber) {
//		List<FoodFunction> foodFunctions = foodFunctionService.getAllFoodFunction(pageNumber, 2);
//		return ResponseEntity.ok().body(foodFunctions);
//	}
	
	//TODO: get all image of food function
	@GetMapping("/foodFunctions/{id}/images")
	ResponseEntity<List<ImageFile>> getAllImageFoodFunction(@PathVariable(value = "id") Long idFoodFunction) {
		List<ImageFile> imageFiles = foodFunctionService.getAllImageFoodFunction(idFoodFunction);
		return ResponseEntity.ok().body(imageFiles);
	}
	
	//TODO: get all food by brand
	@GetMapping("/foodFunctions/brand/{id}")
	ResponseEntity<List<FoodFunction>> getAllFoodFunctionByBrand(@PathVariable(value = "id") Long brandId) {
		List<FoodFunction> foodFunctions = foodFunctionRepository.findByBrand(brandService.findBrandById(brandId));
		return ResponseEntity.ok().body(foodFunctions);
	}
	
	//TODO: get all food by category
	@GetMapping("/foodFunctions/category/{id}")
	ResponseEntity<List<FoodFunction>> getAllFoodFunctionByCategory(@PathVariable(value = "id") Long categoryId) {
		List<FoodFunction> foodFunctions = foodFunctionRepository.findByCategory(categoryService.findCategoryById(categoryId));
		return ResponseEntity.ok().body(foodFunctions);
	}
	
	//TODO: get all food by manufacturer
	@GetMapping("/foodFunctions/manufacturer/{id}")
	ResponseEntity<List<FoodFunction>> getAllFoodFunctionByManufacturer(@PathVariable(value = "id") Long manufacturerId) {
		List<FoodFunction> foodFunctions = foodFunctionRepository.findByManufacturer(manufacturerService.findManufacturerById(manufacturerId));
		return ResponseEntity.ok().body(foodFunctions);
	}
}
