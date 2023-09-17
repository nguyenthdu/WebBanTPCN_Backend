package com.example.backend.services.impl;

import com.example.backend.entities.FoodFunction;
import com.example.backend.repositories.FoodFunctionRepository;
import com.example.backend.services.FoodFunctionService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FoodFunctionServiceImpl implements FoodFunctionService {
    private final FoodFunctionRepository foodFunctionRepository;

    public FoodFunctionServiceImpl(FoodFunctionRepository foodFunctionRepository) {
        this.foodFunctionRepository = foodFunctionRepository;
    }

    @Override
    public FoodFunction createFoodFunction(FoodFunction foodFunction) {
        return foodFunctionRepository.save(foodFunction);
    }



    @Override
    public FoodFunction findFoodFunctionByNameFood(String nameFoodFunction) {
        return foodFunctionRepository.findFoodFunctionByNameFood(nameFoodFunction);
    }




}
