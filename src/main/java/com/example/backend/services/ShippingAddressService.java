package com.example.backend.services;

import com.example.backend.entities.ShippingAddress;

public interface ShippingAddressService {
	ShippingAddress createShippingAddress(ShippingAddress shippingAddress);
	
	ShippingAddress findShippingAddressById(Long idShippingAddress);
	
	void deleteShippingAddressById(Long idShippingAddress);
	
	ShippingAddress updateShippingAddress(ShippingAddress shippingAddress);
	//get all shipping address for user
}
