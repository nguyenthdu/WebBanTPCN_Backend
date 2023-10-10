package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

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
    //kiểu dữ liệu là text
    @Column(columnDefinition = "text")
    private String description;
    private double price;
    @Lob
    @Column(name = "images", columnDefinition = "mediumblob")
    private byte[] images;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Set<Ingredients> ingredients;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "uses_id", nullable = false)
    private Set<Uses> uses;

    private String packingWay;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_object_id", nullable = false)
    private UserObject userObjects;

    private String dosageForm;
    private String placeOfManufacture;
    private int expiryDate;
    private int name;

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
