package com.example.backend.services;

import com.example.backend.entities.Manufacturer;

public interface ManufacturerService {
    Manufacturer  createManufacturer(Manufacturer manufacturer);
    Manufacturer findManufacturerByNameManufacturer(String nameManufacturer);
    Manufacturer findManufacturerById(Long idManufacturer);

}
