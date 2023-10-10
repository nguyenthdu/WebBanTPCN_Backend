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
@Table(name = "user_object")
public class UserObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameObject;
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "food_function_id", nullable = false)
    private Set<FoodFunction> foodFunction;



}
