//package com.example.backend.entities;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "review")
//public class Review {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String content;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id",nullable = false)
//    private User user;
//    @ManyToOne
//    @JoinColumn(name = "food_function",nullable = false)
//    private FoodFunction foodFunction;
//    private LocalDateTime createdAt;
//}
