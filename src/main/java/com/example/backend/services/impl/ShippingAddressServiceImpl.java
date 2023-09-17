package com.example.backend.services.impl;

import com.example.backend.entities.Manufacturer;
import com.example.backend.entities.ShippingAddress;
import com.example.backend.repositories.ManufacturerRepository;
import com.example.backend.repositories.ShippingAddressRepository;
import com.example.backend.services.ShippingAddressService;

public class ShippingAddressServiceImpl implements ShippingAddressService {
    private final ShippingAddressRepository shippingAddressRepository;

    public ShippingAddressServiceImpl(ShippingAddressRepository shippingAddressRepository) {
        this.shippingAddressRepository = shippingAddressRepository;
    }


    @Override
    public ShippingAddress createShippingAddress(ShippingAddress shippingAddress) {
        return shippingAddressRepository.save(shippingAddress);
    }

    @Override
    public ShippingAddress findShippingAddressByShippingAddressName(String nameShippingAddress) {
        return shippingAddressRepository.findShippingAddressByName(nameShippingAddress);

    }

    @Override
    public ShippingAddress findShippingAddressById(Long idShippingAddress) {
        return shippingAddressRepository.findShippingAddressById(idShippingAddress);
    }

    @Override
    public void deleteShippingAddressById(Long idShippingAddress) {
        shippingAddressRepository.deleteById(idShippingAddress);
    }

    @Override
    public ShippingAddress updateShippingAddress(ShippingAddress shippingAddress ) {
        return shippingAddressRepository.save(shippingAddress);
    }
}
