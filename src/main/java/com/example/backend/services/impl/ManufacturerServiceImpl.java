package com.example.backend.services.impl;

import com.example.backend.entities.Manufacturer;
import com.example.backend.repositories.ManufacturerRepository;
import com.example.backend.services.ManufacturerService;
import org.springframework.stereotype.Service;

@Service

public class ManufacturerServiceImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }


    @Override
    public Manufacturer createManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer findManufacturerByNameManufacturer(String nameManufacturer) {
        return manufacturerRepository.findManufacturerByNameManufacturer(nameManufacturer);

    }

    @Override
    public Manufacturer findManufacturerById(Long idManufacturer) {
        return manufacturerRepository.findManufacturerById(idManufacturer);
    }
}
