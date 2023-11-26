package com.example.backend.mappers;

import com.example.backend.dtos.FoodFunctionDto;
import com.example.backend.entities.FoodFunction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FoodMapper {
	FoodFunctionDto toFoodFunctionDto(FoodFunction foodFunction);//chuyển foodFunction thành foodFunctionDto
	
	//chuyển foodFunctionDto thành foodFunction
	FoodFunction toFoodFunction(FoodFunctionDto foodFunctionDto);
}
