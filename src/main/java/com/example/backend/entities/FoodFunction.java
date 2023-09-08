package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String nameFood;
    private String description;
    private double price;
    @ElementCollection
    private List<String> images;
    @ElementCollection
    private List<String> ingredients;
    @ElementCollection
    private List<String> uses;
    private String packingWay;
    @ElementCollection
    private List<String> userObject ;
    private String dosageForm;
    private String placeOfManufacture;
    private int expiryDate;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "manufacturer_id", nullable = false)
    private Manufacturer manufacturer;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

//    @OneToMany(mappedBy = "food_function", cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Review> reviews = new ArrayList<>();

//    @OneToMany(mappedBy = "food_function", cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Rating> ratings = new ArrayList<>();

    @Column(name = "num_rating")
    private int numRating;
}
