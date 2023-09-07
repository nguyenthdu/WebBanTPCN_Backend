//package com.example.backend.entities;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "rating")
//
//public class Rating {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    // mô tả quan hệ ngược lại
//    @ManyToOne
//    @JoinColumn(name = "id", nullable = false)
//    private FoodFunction foodFunction;
//    private double rating;
//}
