package com.example.backend.repositories;

import com.example.backend.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	@Query(value = "UPDATE cart_items SET shopping_cart_id = null WHERE shopping_cart_id = ?1", nativeQuery = true)
	void deleteCartItemById(Long cartId);
}