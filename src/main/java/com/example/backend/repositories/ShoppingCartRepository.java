package com.example.backend.repositories;

import com.example.backend.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
	Optional<ShoppingCart> findByUserId(Long userId);
}