package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	@Lob
	@Column(name = "images", columnDefinition = "mediumblob")
	@JsonIgnore//bỏ qua thuộc tính này khi chuyển thành json
	private byte[] images;
	@Column(columnDefinition = "text")
	private String ingredients;
	private String packingWay;//cách đóng gói
	private String dosageForm;//dạng bào chế
	private String placeOfManufacture;
	private int expiryDate;
	private boolean status = true;
	private int discount = 0;
	@ManyToOne
	@JoinColumn(name = "manufacturer_id", nullable = false)
	@JsonIgnore
	private Manufacturer manufacturer;
	@ManyToOne
	@JoinColumn(name = "brand_id", nullable = false)
	@JsonIgnore
	private Brand brand;
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
