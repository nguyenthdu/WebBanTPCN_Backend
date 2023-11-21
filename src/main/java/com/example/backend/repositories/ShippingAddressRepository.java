package com.example.backend.repositories;

import com.example.backend.entities.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
	ShippingAddress findShippingAddressById(Long idShippingAddress);
	
	List<ShippingAddress> findAllByUserId(Long userId);
	
	ShippingAddress findShippingAddressByIdAndUserId(Long idShippingAddress, Long userId);
}
