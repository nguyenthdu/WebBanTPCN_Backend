package com.example.backend.repositories;

import com.example.backend.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findBrandByNameBrand(String nameBrand);
    Brand findBrandById(Long idBrand);

}
