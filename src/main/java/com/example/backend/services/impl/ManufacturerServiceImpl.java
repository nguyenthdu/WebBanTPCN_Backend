package com.example.backend.services.impl;

import com.example.backend.entities.Manufacturer;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.ManufacturerRepository;
import com.example.backend.services.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {
	private final ManufacturerRepository manufacturerRepository;
	
	@Override
	public Manufacturer createManufacturer(Manufacturer manufacturer) {
		if(manufacturerRepository.findManufacturerByNameManufacturer(manufacturer.getNameManufacturer()) != null) {
			throw new AppException("Name Manufacturer is existed", HttpStatus.BAD_REQUEST);
		}
		return manufacturerRepository.save(manufacturer);
	}
	
	@Override
	public Manufacturer findManufacturerById(Long idManufacturer) {
		return manufacturerRepository.findById(idManufacturer).orElseThrow(() -> new AppException("Id Manufacturer is not existed with id: " + idManufacturer, HttpStatus.NOT_FOUND));
	}
	
	@Override
	public void deleteManufacturerById(Long idManufacturer) {
		manufacturerRepository.deleteById(idManufacturer);
	}
	
	@Override
	public Manufacturer updateManufacturer(Manufacturer manufacturer) {
		return manufacturerRepository.save(manufacturer);
	}
	
	@Override
	public Page<Manufacturer> getManufacturers(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
		return manufacturerRepository.findAll(pageable);
	}
}
