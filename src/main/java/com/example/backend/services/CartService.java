package com.example.backend.services;

public interface CartService {
	void addFoodToCart(Long cartId, Long foodFunctionId, int quantity);
	
	void removeFoodFromCart(Long cartId, Long foodFunctionId);
	
	void updateCartItemQuantity(Long cartItemId, int quantity);
}
