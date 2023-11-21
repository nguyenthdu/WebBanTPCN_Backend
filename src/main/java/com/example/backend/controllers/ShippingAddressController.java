package com.example.backend.controllers;

import com.example.backend.dtos.ErrorDto;
import com.example.backend.entities.ShippingAddress;
import com.example.backend.entities.User_;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.ShippingAddressRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.ShippingAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class ShippingAddressController {
	@Autowired
	private ShippingAddressRepository shippingAddressRepository;
	private final ShippingAddressService shippingAddressService;
	private final UserRepository userRepository;
	
	//TODO: get all shippingAddresses
	@GetMapping("/shippingAddresses")
	List<ShippingAddress> getAllShippingAddresses() {
		return shippingAddressRepository.findAll();
	}
	
	//TODO: create shippingAddress
	@PostMapping("/shippingAddresses")
	ResponseEntity<ErrorDto> createShippingAddress(
			@RequestParam("nameOfRecipient") String receiverName,
			@RequestParam("phoneOfRecipient") String phoneNumber,
			@RequestParam("streetHouseNumber") String address,
			@RequestParam("district") String district,
			@RequestParam("city") String city,
			@RequestParam("nation") String nation,
			@RequestParam("userId") Long userId
	) {
		ShippingAddress shippingAddress = new ShippingAddress();
		User_ user_ = userRepository.findById(userId).orElseThrow(() -> new AppException("User is not existed with id: " + userId, HttpStatus.NOT_FOUND));
		try {
			shippingAddress.setNameOfRecipient(receiverName);
			shippingAddress.setPhoneOfRecipient(phoneNumber);
			shippingAddress.setStreetHouseNumber(address);
			shippingAddress.setDistrict(district);
			shippingAddress.setCity(city);
			shippingAddress.setNation(nation);
			shippingAddress.setUser(user_);
			shippingAddressService.createShippingAddress(shippingAddress);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Create Shipping Address Successfully with id: " + shippingAddress.getId(), HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	//TODO: get shippingAddress by id
	@GetMapping("/shippingAddresses/{id}")
	ResponseEntity<ShippingAddress> getShippingAddressById(@PathVariable Long id) {
		ShippingAddress shippingAddress = shippingAddressService.findShippingAddressById(id);
		return ResponseEntity.ok(shippingAddress);
	}
	
	//TODO: update shippingAddress by id
	@PutMapping("/shippingAddresses")
	ResponseEntity<ErrorDto> updateShippingAddress(
			@RequestParam("shippingAddressId") Long shippingAddressId,
			@RequestParam("nameOfRecipient") String receiverName,
			@RequestParam("phoneOfRecipient") String phoneNumber,
			@RequestParam("streetHouseNumber") String address,
			@RequestParam("district") String district,
			@RequestParam("city") String city,
			@RequestParam("nation") String nation,
			@RequestParam("userId") Long userId
	) {
		ShippingAddress shippingAddress = shippingAddressService.findShippingAddressById(shippingAddressId);
		User_ user_ = userRepository.findById(userId).orElseThrow(() -> new AppException("User is not existed with id: " + userId, HttpStatus.NOT_FOUND));
		try {
			shippingAddress.setNameOfRecipient(receiverName);
			shippingAddress.setPhoneOfRecipient(phoneNumber);
			shippingAddress.setStreetHouseNumber(address);
			shippingAddress.setDistrict(district);
			shippingAddress.setCity(city);
			shippingAddress.setNation(nation);
			shippingAddress.setUser(user_);
			shippingAddressService.updateShippingAddress(shippingAddress);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Update Shipping Address Successfully with id: " + shippingAddress.getId(), HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	//TODO: delete shippingAddress by id
	@DeleteMapping("/shippingAddresses/{id}")
	ResponseEntity<ErrorDto> deleteShippingAddress(@PathVariable Long id) {
		ShippingAddress shippingAddress = shippingAddressService.findShippingAddressById(id);
		try {
			shippingAddressService.deleteShippingAddressById(id);
		} catch (AppException e) {
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Delete Shipping Address Successfully with id: " + id, HttpStatus.OK.value(), Instant.now().toString()));
	}
}
