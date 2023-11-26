package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "food_function")
public class FoodFunction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "food_function_id")
	private Long id;
	private String code;
	@Column(name = "name_food")
	private String nameFood;
	@Column(columnDefinition = "text")
	private String description;
	private double price;
	private int quantity;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "image_id")
	private List<ImageFile> imageFiles = new ArrayList<>();
	@Column(columnDefinition = "text")
	private String ingredients;
	@Column(name = "packing_way")
	private String packingWay;//cách đóng gói
	@Column(name = "dosage_form")
	private String dosageForm;//dạng bào chế
	@Column(name = "place_of_manufacture")
	private String placeOfManufacture;
	@Column(name = "expiry_date")
	private int expiryDate;
	private boolean status = true;
	private int discount = 0;
	@ManyToOne
	@JoinColumn(name = "manufacturer_id", nullable = false)
	private Manufacturer manufacturer;
	@ManyToOne
	@JoinColumn(name = "brand_id", nullable = false)
	private Brand brand;
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;
	//    @OneToMany(mappedBy = "food_function", cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Review> reviews = new ArrayList<>();
//    @OneToMany(mappedBy = "food_function", cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Rating> ratings = new ArrayList<>();
//
}
