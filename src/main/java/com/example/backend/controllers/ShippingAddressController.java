package com.example.backend.controllers;

import com.example.backend.entities.ShippingAddress;
import com.example.backend.repositories.ShippingAddressRepository;
import com.example.backend.services.ShippingAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class ShippingAddressController {
	@Autowired
	private ShippingAddressRepository shippingAddressRepository;
	private final ShippingAddressService shippingAddressService;
	
	//TODO: get all shippingAddresses
	@GetMapping("/shippingAddresses")
	List<ShippingAddress> getAllShippingAddresses() {
		return shippingAddressRepository.findAll();
	}
	
	//TODO: create shippingAddress
	@PostMapping("/shippingAddresses")
	ResponseEntity<String> createShippingAddress(@RequestBody ShippingAddress shippingAddress) {
//      kiểm tra xem một địa chỉ giao hàng đã tồn tại trong danh sách địa chỉ giao hàng của người dùng hay chưa?
		List<ShippingAddress> shippingAddresses = shippingAddressRepository.findShippingAddressesByUserId(shippingAddress.getUser().getId());
//        shippingAddresses.forEach(shippingAddress1 -> {
//            System.out.println("so nha cua user dang dang ky dia chi giao hang moi: " + shippingAddress1.getStreetHouseNumber());
//        });
		for(ShippingAddress shippingAddress1 : shippingAddresses) {
			if(shippingAddress1.getStreetHouseNumber().equals(shippingAddress.getStreetHouseNumber())) {
				return ResponseEntity.ok("ShippingAddress is already exist");
			}
		}
		shippingAddressService.createShippingAddress(shippingAddress);
		return ResponseEntity.ok("Create ShippingAddress Successfully");
	}
	
	//TODO: update shippingAddress
	@PutMapping("/shippingAddresses/{id}")
	ResponseEntity<String> updateShippingAddress(@PathVariable Long id, @RequestBody ShippingAddress shippingAddress) {
		ShippingAddress shippingAddress1 = shippingAddressRepository.findShippingAddressById(id);
		if(shippingAddress1 == null) {
			return ResponseEntity.ok("ShippingAddress is not exist");
		}
		shippingAddress1.setNameOfRecipient(shippingAddress.getNameOfRecipient());
		shippingAddress1.setPhoneOfRecipient(shippingAddress.getPhoneOfRecipient());
		shippingAddress1.setStreetHouseNumber(shippingAddress.getStreetHouseNumber());
		shippingAddress1.setDistrict(shippingAddress.getDistrict());
		shippingAddress1.setCity(shippingAddress.getCity());
		shippingAddress1.setNation(shippingAddress.getNation());
		shippingAddressRepository.save(shippingAddress1);
		return ResponseEntity.ok("Update ShippingAddress Successfully");
	}
	
	//    TODO: delete shippingAddress by id
	@DeleteMapping("/shippingAddresses/{id}")
	ResponseEntity<String> deleteShippingAddressById(@PathVariable Long id) {
		ShippingAddress shippingAddress = shippingAddressRepository.findShippingAddressById(id);
		if(shippingAddress == null) {
			return ResponseEntity.ok("ShippingAddress is not exist");
		}
		shippingAddressRepository.deleteById(id);
		return ResponseEntity.ok("Delete ShippingAddress Successfully");
	}
	
	//    TODO: get shippingAddress by id
	@GetMapping("/shippingAddresses/{id}")
	ResponseEntity<ShippingAddress> getShippingAddress(@PathVariable Long id) {
		ShippingAddress shippingAddress = shippingAddressRepository.findShippingAddressById(id);
		if(shippingAddress == null) {
			return ResponseEntity.ok(null);
		}
		return ResponseEntity.ok(shippingAddress);
	}
	
	//    TODO: get shippingAddress by user id
	@GetMapping("/shippingAddresses/user/{id}")
	ResponseEntity<List<ShippingAddress>> getShippingAddressByUserId(@PathVariable Long id) {
		List<ShippingAddress> shippingAddresses = shippingAddressRepository.findShippingAddressesByUserId(id);
		if(shippingAddresses == null) {
			return ResponseEntity.ok(null);
		}
		return ResponseEntity.ok(shippingAddresses);
	}
}
