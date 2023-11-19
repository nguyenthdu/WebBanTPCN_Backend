package com.example.backend.services.impl;

import com.example.backend.entities.Category;
import com.example.backend.entities.TypeFood;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.TypeFoodRepository;
import com.example.backend.services.TypeFoodService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TypeFoodServiceImpl implements TypeFoodService {
	private final TypeFoodRepository typeFoodRepository;
	
	public TypeFoodServiceImpl(TypeFoodRepository typeFoodRepository) {
		this.typeFoodRepository = typeFoodRepository;
	}
	
	//TODO: method create new type food
	@Override
	public TypeFood createTypeFood(TypeFood typeFood) {
		if(typeFoodRepository.findTypeFoodByName(typeFood.getName()) != null) {
			throw new AppException("Name Type Food is existed", HttpStatus.BAD_REQUEST);
		}
		return typeFoodRepository.save(typeFood);
	}
	
	//TODO: method find type food by id
	@Override
	public TypeFood findTypeFoodById(Long typeFoodId) {
		return typeFoodRepository.findById(typeFoodId).orElseThrow(() -> new AppException("Id Type Food is not existed with id: " + typeFoodId, HttpStatus.NOT_FOUND));
	}
//	@Override
//	public TypeFood findTypeFoodByNameTypeFood(String nameTypeFood) {
//		return typeFoodRepository.findTypeFoodByName(nameTypeFood);
//	}
	//TODO: method delete uses by id
	
	@Override
	public void deleteTypeFoodById(Long typeFoodId) {
		typeFoodRepository.deleteById(typeFoodId);
	}
	
	//TODO: method update uses
	@Override
	public TypeFood updateTypeFood(TypeFood typeFood) {
		return typeFoodRepository.save(typeFood);
	}
	
	@Override
	public Set<Category> getAllCategoriesByTypeFood(TypeFood typeFood) {
		return typeFood.getCategories();
	}
}
