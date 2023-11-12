package com.example.backend.services;

import com.example.backend.entities.ShoppingCart;

public interface ShoppingCartService {
	void addFoodToCart(Long userId, Long foodFunctionId, int quantity);
	
	void removeFoodFromCart(Long cartId, Long foodFunctionId);
	
	void updateCartItemQuantity(Long cartItemId, int quantity);
	
	ShoppingCart getOrCreateCartForUser(Long userId);
	
	ShoppingCart getCartForUser(Long userId);
}
