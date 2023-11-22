package com.example.backend.services.impl;

import com.example.backend.entities.ShippingAddress;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.ShippingAddressRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.ShippingAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShippingAddressImpl implements ShippingAddressService {
	private final ShippingAddressRepository shippingAddressRepository;
	private final UserRepository userRepository;
	
	@Override
	public ShippingAddress createShippingAddress(ShippingAddress shippingAddress) {
		return shippingAddressRepository.save(shippingAddress);
	}
	
	@Override
	public ShippingAddress findShippingAddressById(Long idShippingAddress) {
		return shippingAddressRepository.findById(idShippingAddress).orElseThrow(() -> new AppException("Id Shipping Address is not existed with id: " + idShippingAddress, HttpStatus.NOT_FOUND));
	}
	
	@Override
	public void deleteShippingAddressById(Long idShippingAddress) {
		shippingAddressRepository.deleteById(idShippingAddress);
	}
	
	@Override
	public ShippingAddress updateShippingAddress(ShippingAddress shippingAddress) {
		return shippingAddressRepository.save(shippingAddress);
	}
	
	@Override
	public List<ShippingAddress> findAllShippingAddressByUserId(Long userId) {
		return shippingAddressRepository.findAllByUserId(userId);
	}
	
	@Override
	public ShippingAddress findShippingAddressByIdAndUserId(Long idShippingAddress, Long userId) {
		shippingAddressRepository.findById(idShippingAddress).orElseThrow(() -> new AppException("Id Shipping Address is not existed with id: " + idShippingAddress, HttpStatus.NOT_FOUND));
		userRepository.findById(userId).orElseThrow(() -> new AppException("User is not existed with id: " + userId, HttpStatus.NOT_FOUND));
		return shippingAddressRepository.findShippingAddressByIdAndUserId(idShippingAddress, userId);
	}
}
