package com.example.backend.repositories;

import com.example.backend.entities.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
	Manufacturer findManufacturerByNameManufacturer(String nameManufacturer);
}
