package com.example.backend.services;

import com.example.backend.entities.FoodFunction;

import java.util.List;

public interface FoodFunctionService {
    FoodFunction createFoodFunction(FoodFunction foodFunction);
    FoodFunction findFoodFunctionByNameFood(String nameFoodFunction);

}
