package com.example.backend.services.impl;

import com.example.backend.entities.Brand;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.BrandRepository;
import com.example.backend.services.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {
	private final BrandRepository brandRepository;
	
	public BrandServiceImpl(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}
	
	@Override
	public Brand createBrand(Brand brand) {
		if(brandRepository.findBrandByNameBrand(brand.getNameBrand()) != null) {
			throw new AppException("Name Brand is existed", HttpStatus.BAD_REQUEST);
		}
		return brandRepository.save(brand);
	}
	
	@Override
	public Brand findBrandByNameBrand(String nameBrand) {
		return brandRepository.findBrandByNameBrand(nameBrand);
	}
	
	@Override
	public Brand findBrandById(Long idBrand) {
		//nếu không tìm thấy thì báo lõi:
		return brandRepository.findById(idBrand).orElseThrow(() -> new AppException("Id Brand is not existed with id: " + idBrand, HttpStatus.NOT_FOUND));
	}
	
	@Override
	public void deleteBrandById(Long idBrand) {
		brandRepository.deleteById(idBrand);
	}
	
	@Override
	public Brand updateBrand(Brand brand) {
		return brandRepository.save(brand);
	}
}
