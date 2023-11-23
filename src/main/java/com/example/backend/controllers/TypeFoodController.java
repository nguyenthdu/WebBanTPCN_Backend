package com.example.backend.controllers;

import com.example.backend.dtos.ErrorDto;
import com.example.backend.entities.PageResponse;
import com.example.backend.entities.TypeFood;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.TypeFoodRepository;
import com.example.backend.services.TypeFoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class TypeFoodController {
	@Autowired//tự động tìm kiếm và tiêm vào đối tượng
	private TypeFoodRepository typeFoodRepository;
	private final TypeFoodService typeFoodService;
	
	//TODO: get all uses
	@GetMapping("/typeFoods")
	List<TypeFood> getAllTypeFood() {
		return typeFoodRepository.findAll();
	}
	
	//TODO: create typeFood
	@PostMapping("/typeFoods")
	ResponseEntity<ErrorDto> createTypeFood(@RequestBody TypeFood typeFood) {
		try {
			typeFoodService.createTypeFood(typeFood);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Create TypeFood Successfully with id: " + typeFood.getId(), HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	//TODO: get typeFood by id
	@GetMapping("/typeFoods/{typeFoodId}")
	ResponseEntity<TypeFood> getTypeFoodById(@PathVariable Long typeFoodId) {
		TypeFood typeFood = typeFoodService.findTypeFoodById(typeFoodId);
		return ResponseEntity.ok(typeFood);
	}
	
	//TODO: update typeFood
	@PutMapping("/typeFoods")
	ResponseEntity<ErrorDto> updateTypeFood(@RequestBody TypeFood typeFood) {
		TypeFood typeFood1 = typeFoodService.findTypeFoodById(typeFood.getId());
		try {
			typeFoodService.updateTypeFood(typeFood);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Update TypeFood Successfully with id: " + typeFood1.getId(), HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	//TODO: delete typeFood by id
	@DeleteMapping("/typeFoods/{typeFoodId}")
	ResponseEntity<ErrorDto> deleteTypeFoodById(@PathVariable Long typeFoodId) {
		TypeFood typeFood = typeFoodService.findTypeFoodById(typeFoodId);
		try {
			typeFoodService.deleteTypeFoodById(typeFoodId);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Delete TypeFood Successfully with id: " + typeFood.getId(), HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	@GetMapping("/typeFoods/page/{pageNumber}")
	public ResponseEntity<PageResponse<TypeFood>> getAllFoodFunctionsByPage(@PathVariable(value = "pageNumber") int pageNumber) {
		int pageSize = 10; // Số lượng mục trên mỗi trang
		Page<TypeFood> pageResult = typeFoodService.getTypeFoods(pageNumber, pageSize);
		List<TypeFood> typeFoods = pageResult.getContent();
		PageResponse<TypeFood> pageResponse = new PageResponse<>();
		pageResponse.setContent(typeFoods);
		pageResponse.setTotalPages(pageResult.getTotalPages());
		pageResponse.setTotalElements(pageResult.getTotalElements());
		pageResponse.setCurrentPage(pageNumber);
		pageResponse.setPageSize(pageSize);
		return ResponseEntity.ok().body(pageResponse);
	}
}
