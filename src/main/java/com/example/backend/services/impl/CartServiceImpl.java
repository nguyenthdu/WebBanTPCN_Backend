package com.example.backend.services.impl;

import com.example.backend.entities.Cart;
import com.example.backend.entities.CartItem;
import com.example.backend.entities.FoodFunction;
import com.example.backend.repositories.CartItemRepository;
import com.example.backend.repositories.CartRepository;
import com.example.backend.repositories.FoodFunctionRepository;
import com.example.backend.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
	private final CartRepository cartRepository;
	private final FoodFunctionRepository foodFunctionRepository;
	private final CartItemRepository cartItemRepository;
	
	@Override
	public void addFoodToCart(Long cartId, Long foodFunctionId, int quantity) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));
		FoodFunction foodFunction = foodFunctionRepository.findById(foodFunctionId).orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));
		CartItem cartItem = new CartItem();
		cartItem.setCart(cart);
		cartItem.setFoodFunction(foodFunction);
		cartItem.setQuantity(quantity);
		cart.getCartItems().add(cartItem);
		cartRepository.save(cart);
	}
	
	@Override
	public void removeFoodFromCart(Long cartId, Long foodFunctionId) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Không tìm thấy giỏ hàng"));
		cart.getCartItems().removeIf(cartItem -> cartItem.getFoodFunction().getId().equals(foodFunctionId));
		cartRepository.save(cart);
	}
	
	@Override
	public void updateCartItemQuantity(Long cartItemId, int quantity) {
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new RuntimeException("Không tìm thấy mục giỏ hàng"));
		cartItem.setQuantity(quantity);
		cartItemRepository.save(cartItem);
	}
}
