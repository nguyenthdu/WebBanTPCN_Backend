package com.example.backend.services.impl;

import com.example.backend.entities.FoodFunction;
import com.example.backend.entities.ImageFile;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.FoodFunctionRepository;
import com.example.backend.services.FoodFunctionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodFunctionServiceImpl implements FoodFunctionService {
	private final FoodFunctionRepository foodFunctionRepository;
	
	public FoodFunctionServiceImpl(FoodFunctionRepository foodFunctionRepository) {
		this.foodFunctionRepository = foodFunctionRepository;
	}
	
	//Xử lý random code cho food function
	public String randomCode() {
		String code = "";
		double n = (Math.random()) * ((100000 - 1) + 1) + 1;//random từ 1 đến 100000
		int i = (int) n, a = 0;
		do {
			if(i < 10) {//nếu i < 10 thì thêm vào chuỗi code là TPCN0000 + i
				code = "TPCN0000" + i;
			}
			if(i < 100) {
				code = "TPCN000" + i;
			}
			if(i < 1000) {
				code = "TPCN00" + i;
			}
			if(i < 10000) {
				code = "TPCN0" + i;
			}
			if(i < 100000) {
				code = "TPCN" + i;
			}
			if(foodFunctionRepository.findFoodFunctionByCode(code) != null) {
				a = 1;//nếu code đã tồn tại thì a = 1
			} else {
				a = 0;
			}
		} while(a != 0);
		return code;
	}
	
	@Override
	public FoodFunction createFoodFunction(FoodFunction foodFunction) {
		foodFunction.setCode(randomCode());
		foodFunction.setStatus(true);
		foodFunction.setDiscount(0);
		if(foodFunctionRepository.findFoodFunctionByNameFood(foodFunction.getNameFood()) != null) {
			throw new AppException("Name Food is existed", HttpStatus.BAD_REQUEST);
		}
		return foodFunctionRepository.save(foodFunction);
	}
	
	@Override
	public FoodFunction findFoodFunctionByNameFood(String nameFoodFunction) {
		if(foodFunctionRepository.findFoodFunctionByNameFood(nameFoodFunction) == null) {
			throw new AppException("Not found food function with name: " + nameFoodFunction, HttpStatus.NOT_FOUND);
		}
		return foodFunctionRepository.findFoodFunctionByNameFood(nameFoodFunction);
	}
	
	@Override
	public FoodFunction findFoodFunctionById(Long idFoodFunction) {
		return foodFunctionRepository.findById(idFoodFunction)
				.orElseThrow(() -> new AppException("Not found food function with id: " + idFoodFunction, HttpStatus.NOT_FOUND));
	}
	
	@Override
	public void deleteFoodFunctionById(Long foodFunctionId) {
		foodFunctionRepository.deleteById(foodFunctionId);
	}
	
	@Override
	public FoodFunction updateFoodFunction(FoodFunction foodFunction) {
		//nếu như không tìm thấy brand hoặc category hoặc manufacturer thì báo lỗi
		return foodFunctionRepository.save(foodFunction);
	}
	
	@Override
	public List<FoodFunction> getAllFoodFunction(int pageNumber, int pageSize) {
		//trang bắt đầu từ 1:pageNumber - 1
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		Page<FoodFunction> pageResult = foodFunctionRepository.findAll(pageable);
		if(pageResult.hasContent()) {
			return pageResult.getContent();
		} else {
			throw new AppException("Page " + pageNumber + " is not existed", HttpStatus.NOT_FOUND);
		}
	}
	
	@Override
	public List<ImageFile> getAllImageFoodFunction(Long idFoodFunction) {
		FoodFunction foodFunction = foodFunctionRepository.findById(idFoodFunction)
				.orElseThrow(() -> new AppException("Not found food function with id: " + idFoodFunction, HttpStatus.NOT_FOUND));
		return foodFunction.getImageFiles();
	}
}



