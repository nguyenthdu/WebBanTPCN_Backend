package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "uses")
public class Uses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    //1 uses có nhiều food function
    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "food_function_id", nullable = false)
    private Set<FoodFunction> foodFunctions ;


}
