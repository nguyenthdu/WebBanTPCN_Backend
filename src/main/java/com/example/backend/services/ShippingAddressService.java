package com.example.backend.services;

import com.example.backend.entities.Category;
import com.example.backend.entities.FoodFunction;
import com.example.backend.entities.ShippingAddress;

import java.util.Set;

public interface ShippingAddressService {
    ShippingAddress createShippingAddress(ShippingAddress ShippingAddress);
    ShippingAddress findShippingAddressByShippingAddressName(String nameShippingAddress);
    ShippingAddress findShippingAddressById(Long idShippingAddress);
    void deleteShippingAddressById(Long idShippingAddress);
    ShippingAddress updateShippingAddress(ShippingAddress shippingAddress);
}
