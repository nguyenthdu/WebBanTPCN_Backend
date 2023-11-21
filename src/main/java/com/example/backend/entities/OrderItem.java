//package com.example.backend.entities;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//
//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "order_item")
//public class OrderItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "order_id", nullable = false)
//    private Order_ order;
//
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @ManyToOne(cascade = CascadeType.ALL)
//    private FoodFunction foodFunction;
//    private int totalItem;
//    private double totalPrice;
//}
