package com.example.backend.services;

import com.example.backend.entities.Brand;
import org.springframework.data.domain.Page;

public interface BrandService {
	Brand createBrand(Brand brand);
	
	Brand findBrandByNameBrand(String nameBrand);
	
	Brand findBrandById(Long idBrand);
	
	void deleteBrandById(Long idBrand);
	
	Brand updateBrand(Brand brand);
	
	Page<Brand> getBrands(int pageNumber, int pageSize);
}
