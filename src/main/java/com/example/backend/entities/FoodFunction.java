package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "food_function")
public class FoodFunction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String code;
	private String nameFood;
	@Column(columnDefinition = "text")
	private String description;
	private double price;
	private int quantity;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "image_id")
	private List<ImageFile> imageFiles = new ArrayList<>();
	@Column(columnDefinition = "text")
	private String ingredients;
	private String packingWay;//cách đóng gói
	private String dosageForm;//dạng bào chế
	private String placeOfManufacture;
	private int expiryDate;
	private boolean status = true;
	private int discount = 0;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn(name = "manufacturer_id", nullable = false)
	@JsonIgnore
	private Manufacturer manufacturer;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn(name = "brand_id", nullable = false)
	@JsonIgnore
	private Brand brand;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	@JsonIgnore
	private Category category;
	//    @OneToMany(mappedBy = "food_function", cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Review> reviews = new ArrayList<>();
//    @OneToMany(mappedBy = "food_function", cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Rating> ratings = new ArrayList<>();
//
}
