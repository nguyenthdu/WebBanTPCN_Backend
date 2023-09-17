package com.example.backend.services;

<<<<<<< HEAD
public interface ShippingAddressService {

=======
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
>>>>>>> 29470ea1212cb64ce8a5be2cc3ba8cbe147e5bc1
}
