package com.example.backend.repositories;

import com.example.backend.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	List<CartItem> findByCartId(Long cartId);
	
	Optional<CartItem> findByCartIdAndFoodFunctionId(Long cartId, Long foodFunctionId);
}