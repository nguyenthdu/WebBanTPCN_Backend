package com.example.backend.services;

import com.example.backend.entities.Manufacturer;
import org.springframework.data.domain.Page;

public interface ManufacturerService {
	Manufacturer createManufacturer(Manufacturer manufacturer);
	
	Manufacturer findManufacturerById(Long idManufacturer);
	
	void deleteManufacturerById(Long idManufacturer);
	
	Manufacturer updateManufacturer(Manufacturer manufacturer);
	
	Page<Manufacturer> getManufacturers(int pageNumber, int pageSize);
}
