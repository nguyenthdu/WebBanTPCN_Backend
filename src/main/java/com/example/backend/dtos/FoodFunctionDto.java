package com.example.backend.dtos;

import com.example.backend.entities.Brand;
import com.example.backend.entities.Category;
import com.example.backend.entities.ImageFile;
import com.example.backend.entities.Manufacturer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodFunctionDto {
	private Long id;
	private String code;
	private String nameFood;
	private String description;
	private double price;
	private int quantity;
	private List<ImageFile> imageFiles;
	private String ingredients;
	private String packingWay;//cách đóng gói
	private String dosageForm;//dạng bào chế
	private String placeOfManufacture;
	private int expiryDate;
	private boolean status = true;
	private int discount = 0;
	private Manufacturer manufacturer;
	private Brand brand;
	private Category category;
}
