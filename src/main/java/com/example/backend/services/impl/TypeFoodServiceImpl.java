package com.example.backend.services.impl;

import com.example.backend.entities.TypeFood;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.TypeFoodRepository;
import com.example.backend.services.TypeFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeFoodServiceImpl implements TypeFoodService {
	private final TypeFoodRepository typeFoodRepository;
	
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
	public Page<TypeFood> getTypeFoods(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return typeFoodRepository.findAll(pageable);
	}
}
