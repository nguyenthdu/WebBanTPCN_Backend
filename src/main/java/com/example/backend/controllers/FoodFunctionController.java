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
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/foodFunctions")
public class FoodFunctionController {
  private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));

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
   // @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createFoodFunction(@RequestBody FoodFunction foodFunction) {
        FoodFunction foodFunc = new FoodFunction();
        foodFunc.setNameFood(foodFunction.getNameFood());
        if (foodFunctionService.findFoodFunctionByNameFood(foodFunc.getNameFood()) != null) {
            return ResponseEntity.badRequest().body("Name Food Function is required");
        }
        foodFunction.setDescription(foodFunction.getDescription());
        foodFunction.setPrice(foodFunction.getPrice());
        foodFunction.setImages(foodFunction.getImages());
        foodFunction.setIngredients(foodFunction.getIngredients());
        foodFunction.setUses(foodFunction.getUses());
        foodFunction.setPackingWay(foodFunction.getPackingWay());
        foodFunction.setUserObject(foodFunction.getUserObject());
        foodFunction.setDosageForm(foodFunction.getDosageForm());
        foodFunction.setPlaceOfManufacture(foodFunction.getPlaceOfManufacture());
        foodFunction.setExpiryDate(foodFunction.getExpiryDate());

        // Lấy thông tin Manufacturer, Brand và Category từ dữ liệu được gửi từ client
        Manufacturer manufacturer = manufacturerService.findManufacturerById(foodFunction.getManufacturer().getId());
        Brand brand = brandService.findBrandById(foodFunction.getBrand().getId());
        Category category = categoryService.findCategoryById(foodFunction.getCategory().getId());

        foodFunction.setManufacturer(manufacturer);
        foodFunction.setBrand(brand);
        foodFunction.setCategory(category);

        foodFunctionService.createFoodFunction(foodFunction);
        return ResponseEntity.ok("Create Food Function Successfully");
    }
//    public ResponseEntity<String> createFoodFunction(
//         @RequestParam String nameFood,
//            @RequestParam String description,
//            @RequestParam double price,
//            @RequestParam MultipartFile[] image,
//            @RequestParam String[] ingredients,
//            @RequestParam String[] uses,
//            @RequestParam String packingWay,
//            @RequestParam String[] userObject,
//            @RequestParam String dosageForm,
//            @RequestParam String placeOfManufacture,
//            @RequestParam int expiryDate,
//            @RequestParam Long manufacturerId,
//            @RequestParam Long brandId,
//            @RequestParam Long categoryId
//
//    ) throws IOException {
//Path staticPath = Paths.get("static");
//        Path imagePath = Paths.get("images");
//        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
//            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
//        }
//        //Lấy tất cả các file ảnh được gửi từ client
//        List<MultipartFile> multipartFiles = Arrays.asList(image);
//        //Lưu các file ảnh vào thư mục static/images
//        for (MultipartFile multipartFile : multipartFiles) {
//            String fileName = multipartFile.getOriginalFilename();
//            OutputStream outputStream = Files.newOutputStream(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath).resolve(fileName));
//            outputStream.write(multipartFile.getBytes());
//            outputStream.close();
//        }
//        FoodFunction foodFunction = new FoodFunction();
//        foodFunction.setNameFood(nameFood);
//        if (foodFunctionService.findFoodFunctionByNameFood(foodFunction.getNameFood()) != null) {
//            return ResponseEntity.badRequest().body("Name Food Function is required");
//        }
//        foodFunction.setDescription(description);
//        foodFunction.setPrice(price);
//        //Lưu path của các file ảnh vào database
//        List<String> images = multipartFiles.stream().map(multipartFile -> "/images/" + multipartFile.getOriginalFilename()).toList();
//        foodFunction.setImages(images);
//        foodFunction.setIngredients(Arrays.asList(ingredients));
//        foodFunction.setUses(Arrays.asList(uses));
//        foodFunction.setPackingWay(packingWay);
//        foodFunction.setUserObject(Arrays.asList(userObject));
//        foodFunction.setDosageForm(dosageForm);
//        foodFunction.setPlaceOfManufacture(placeOfManufacture);
//        foodFunction.setExpiryDate(expiryDate);
//
//        // Lấy thông tin Manufacturer, Brand và Category từ dữ liệu được gửi từ client
//        Manufacturer manufacturer = manufacturerService.findManufacturerById(manufacturerId);
//        Brand brand = brandService.findBrandById(brandId);
//        Category category = categoryService.findCategoryById(categoryId);
//
//        foodFunction.setManufacturer(manufacturer);
//        foodFunction.setBrand(brand);
//        foodFunction.setCategory(category);
//
//
//        foodFunctionService.createFoodFunction(foodFunction);
//        return ResponseEntity.ok("Create Food Function Successfully");
//
//    }



}
