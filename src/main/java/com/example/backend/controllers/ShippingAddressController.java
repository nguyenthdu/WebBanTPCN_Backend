package com.example.backend.controllers;

import com.example.backend.entities.Category;
import com.example.backend.entities.FoodFunction;
import com.example.backend.entities.ShippingAddress;
import com.example.backend.repositories.CategoryRepository;
import com.example.backend.repositories.ShippingAddressRepository;
import com.example.backend.services.CategoryService;
import com.example.backend.services.ShippingAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/shippingAddress")
public class ShippingAddressController {
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;
    private final ShippingAddressService shippingAddressService;

    //TODO: get all ShippingAddress
    @GetMapping("/getAllShippingAddress")
    List<ShippingAddress> getAllShippingAddress(){
        return shippingAddressRepository.findAll();
    }

    //TODO: create ShippingAddress
    @PostMapping("/createShippingAddress")
    ResponseEntity<String> createShippingAddress(@RequestBody ShippingAddress shippingAddress){
        shippingAddressService.createShippingAddress(shippingAddress);
        return ResponseEntity.ok("Create ShippingAddress Successfully");
    }


    //TODO: get Category by Category name
    @GetMapping("/findShippingAddressByShippingAddressName/{nameShippingAddress}")
    ShippingAddress findShippingAddressByShippingAddressName(@PathVariable String nameShippingAddress) {
        return shippingAddressRepository.findShippingAddressByName(nameShippingAddress);
    }

    //TODO: update category
    @PutMapping("/updateShippingAddress")
    ResponseEntity<String> updateShippingAddress(@RequestBody ShippingAddress shippingAddress) {
        if (shippingAddressService.findShippingAddressById(shippingAddress.getId()) == null) {
            return ResponseEntity.badRequest().body("Id ShippingAddress is not existed");
        }

        shippingAddressService.updateShippingAddress(shippingAddress);
        return ResponseEntity.ok("Update ShippingAddress Successfully");
    }

    //TODO: delete ShippingAddress by id
    @DeleteMapping("/deleteShippingAddressById/{idShippingAddress}")
    ResponseEntity<String> deleteShippingAddressById(@PathVariable Long idShippingAddress) {
        if (shippingAddressService.findShippingAddressById(idShippingAddress) == null) {
            return ResponseEntity.badRequest().body("Id ShippingAddress is not existed");
        }
        shippingAddressService.deleteShippingAddressById(idShippingAddress);
        return ResponseEntity.ok("Delete ShippingAddress Successfully");
    }
}
