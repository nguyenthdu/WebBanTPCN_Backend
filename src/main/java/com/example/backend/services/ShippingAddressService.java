package com.example.backend.services;

import com.example.backend.entities.ShippingAddress;

import java.util.List;

public interface ShippingAddressService {
	ShippingAddress createShippingAddress(ShippingAddress shippingAddress);
	
	ShippingAddress findShippingAddressById(Long idShippingAddress);
	
	void deleteShippingAddressById(Long idShippingAddress);
	
	ShippingAddress updateShippingAddress(ShippingAddress shippingAddress);
	
	//get all shipping address for user
	List<ShippingAddress> findAllShippingAddressByUserId(Long userId);
	
	//get shipping address by id and user id
	ShippingAddress findShippingAddressByIdAndUserId(Long idShippingAddress, Long userId);
}
