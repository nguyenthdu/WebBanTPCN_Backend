package com.example.backend.controllers;

import com.example.backend.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CartController {
	private final ShoppingCartService shoppingCartService;
	
	// Thêm sản phẩm vào giỏ hàng
	@PostMapping("/carts")
	public ResponseEntity<String> addFoodToCart(@RequestParam Long cartId, @RequestParam Long foodFunctionId, @RequestParam int quantity) {
		shoppingCartService.addFoodToCart(cartId, foodFunctionId, quantity);
		return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng");
	}
	
	// Xóa sản phẩm khỏi giỏ hàng
	@DeleteMapping("/carts")
	public ResponseEntity<String> removeFoodFromCart(@RequestParam Long cartId, @RequestParam Long foodFunctionId) {
		shoppingCartService.removeFoodFromCart(cartId, foodFunctionId);
		return ResponseEntity.ok("Sản phẩm đã được xóa khỏi giỏ hàng");
	}
	
	// Cập nhật số lượng sản phẩm trong giỏ hàng
	@PutMapping("/carts")
	public ResponseEntity<String> updateCartItemQuantity(@RequestParam Long cartItemId, @RequestParam int quantity) {
		shoppingCartService.updateCartItemQuantity(cartItemId, quantity);
		return ResponseEntity.ok("Số lượng sản phẩm đã được cập nhật");
	}
}
