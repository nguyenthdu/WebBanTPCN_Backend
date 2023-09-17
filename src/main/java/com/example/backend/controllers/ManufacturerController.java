package com.example.backend.controllers;

import com.example.backend.entities.Manufacturer;
import com.example.backend.repositories.ManufacturerRepository;
import com.example.backend.services.ManufacturerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/manufacturers")
public class ManufacturerController {
    @Autowired
    private ManufacturerRepository manufacturerRepository;
    private final ManufacturerService  manufacturerService;
    //TODO: get all manufacturers
    @GetMapping("/getAllManufacturers")
    List<Manufacturer> getAllManufacturers(){
        return manufacturerRepository.findAll();
    }
    //TODO: create manufacturer
    @PostMapping("/createManufacturer")
    ResponseEntity<String> createManufacturer(@RequestBody Manufacturer manufacturer){
        if(manufacturerService.findManufacturerByNameManufacturer(manufacturer.getNameManufacturer())!=null){
            return ResponseEntity.badRequest().body("Name Manufacturer is required");
        }
        manufacturerService.createManufacturer(manufacturer);
        return ResponseEntity.ok("Create Manufacturer Successfully");
    }

}
