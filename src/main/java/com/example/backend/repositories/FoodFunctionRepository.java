package com.example.backend.repositories;

import com.example.backend.entities.FoodFunction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface FoodFunctionRepository extends JpaRepository<FoodFunction, Long> {
    FoodFunction findFoodFunctionByNameFood(String nameFoodFunction);
    //find food function by ingredients
}
