package com.example.backend.repositories;

import com.example.backend.entities.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
<<<<<<< HEAD
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long>{
=======
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
    ShippingAddress findShippingAddressById(Long idShippingAddress);

    ShippingAddress findShippingAddressByName(String nameShippingAddress);
>>>>>>> 29470ea1212cb64ce8a5be2cc3ba8cbe147e5bc1
}
