package com.example.backend.services.impl;

import com.example.backend.controllers.BrandController;
import com.example.backend.entities.Brand;

import com.example.backend.repositories.BrandRepository;
import com.example.backend.services.BrandService;
import org.springframework.stereotype.Service;


@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public Brand createBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand findBrandByNameBrand(String nameBrand) {
        return brandRepository.findBrandByNameBrand(nameBrand);
    }

    @Override
    public Brand findBrandById(Long idBrand) {
        return brandRepository.findBrandById(idBrand);
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
