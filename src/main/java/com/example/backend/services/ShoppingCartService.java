package com.example.backend.services;

import com.example.backend.dtos.FoodFunctionDto;
import com.example.backend.entities.ShoppingCart;

public interface ShoppingCartService {
	//	void addFoodToCart(Long userId, Long foodFunctionId, int quantity);
	ShoppingCart addFoodToCart(FoodFunctionDto foodFunctionDto, int quantity, String username);
	
	//	void removeFoodFromCart(Long shoppingCartId, Long cartItemId);
	ShoppingCart removeFoodFromCart(FoodFunctionDto foodFunctionDto, String username);
	
	//	void updateCartItemQuantity(Long cartItemId, int quantity);
	ShoppingCart updateCartItemQuantity(FoodFunctionDto foodFunctionDto, int quantity, String username);
	
	ShoppingCart getCartByUsername(String username);
	
	ShoppingCart getOrCreateCartForUser(Long userId);
	
	ShoppingCart getCartForUser(Long userId);
	
	void clearShoppingCart(Long id);
}
