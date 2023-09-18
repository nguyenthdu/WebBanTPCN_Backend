package com.example.backend.controllers;

import com.example.backend.entities.Category;
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

    //TODO: get Manufacturer by Manufacturer name
    @GetMapping("/findManufacturerByManufacturerName/{nameManufacturer}")
    Manufacturer findManufacturerByManufacturerName(@PathVariable String nameManufacturer) {
        return manufacturerRepository.findManufacturerByNameManufacturer(nameManufacturer);
    }

    //TODO: update Manufacturer
    @PutMapping("/updateManufacturer")
    ResponseEntity<String> updateManufacturer(@RequestBody Manufacturer manufacturer) {
        if (manufacturerService.findManufacturerById(manufacturer.getId()) == null) {
            return ResponseEntity.badRequest().body("Id Manufacturer is not existed");
        }

        manufacturerService.updateManufacturer(manufacturer);
        return ResponseEntity.ok("Update Manufacturer Successfully");
    }

    //TODO: delete Manufacturer by id
    @DeleteMapping("/deleteManufacturerById/{idManufacturer}")
    ResponseEntity<String> deleteManufacturerById(@PathVariable Long idManufacturer) {
        if (manufacturerService.findManufacturerById(idManufacturer) == null) {
            return ResponseEntity.badRequest().body("Id Manufacturer is not existed");
        }
        manufacturerService.deleteManufacturerById(idManufacturer);
        return ResponseEntity.ok("Delete Manufacturer Successfully");
    }

}
