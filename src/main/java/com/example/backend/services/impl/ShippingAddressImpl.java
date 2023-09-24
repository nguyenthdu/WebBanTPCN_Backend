package com.example.backend.services.impl;

import com.example.backend.entities.ShippingAddress;
import com.example.backend.repositories.ShippingAddressRepository;
import com.example.backend.repositories.UserRepository;
import com.example.backend.services.ShippingAddressService;
import org.springframework.stereotype.Service;

@Service
public class ShippingAddressImpl implements ShippingAddressService {
    private final ShippingAddressRepository shippingAddressRepository;
    private final UserRepository userRepository;

    public ShippingAddressImpl(ShippingAddressRepository shippingAddressRepository, UserRepository userRepository) {
        this.shippingAddressRepository = shippingAddressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ShippingAddress createShippingAddress(ShippingAddress shippingAddress) {
        return shippingAddressRepository.save(shippingAddress);
    }
}
