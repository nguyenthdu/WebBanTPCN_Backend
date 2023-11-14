package com.example.backend.controllers;

import com.example.backend.entities.ShoppingCart;
import com.example.backend.repositories.ShoppingCartRepository;
import com.example.backend.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ShoppingCartController {
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	private final ShoppingCartService shoppingCartService;
	
	// Thêm sản phẩm vào giỏ hàng
	@PostMapping("/carts")
	public ResponseEntity<String> addFoodToCart(@RequestParam Long userId, @RequestParam Long foodFunctionId, @RequestParam int quantity) {
		try {
			shoppingCartService.addFoodToCart(userId, foodFunctionId, quantity);
			return ResponseEntity.ok("Add food to cart successfully");
		} catch (RuntimeException e) {
			// Xử lý ngoại lệ khi thêm sản phẩm không thành công
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	// Xóa sản phẩm khỏi giỏ hàng
	@DeleteMapping("/carts")
	public ResponseEntity<String> removeFoodFromCart(@RequestParam Long cartId, @RequestParam Long foodFunctionId) {
		try {
			shoppingCartService.removeFoodFromCart(cartId, foodFunctionId);
			return ResponseEntity.ok("Remove food from cart successfully");
		} catch (RuntimeException e) {
			// Xử lý ngoại lệ khi xóa sản phẩm không thành công
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	// Cập nhật số lượng sản phẩm trong giỏ hàng
	@PutMapping("/carts")
	public ResponseEntity<String> updateCartItemQuantity(@RequestParam Long cartItemId, @RequestParam int quantity) {
		try {
			shoppingCartService.updateCartItemQuantity(cartItemId, quantity);
			return ResponseEntity.ok("Update cart item quantity successfully");
		} catch (RuntimeException e) {
			// Xử lý ngoại lệ khi cập nhật số lượng sản phẩm không thành công
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	// Lấy thông tin giỏ hàng
	@GetMapping("/carts")
	public ResponseEntity<ShoppingCart> getCart(@RequestParam Long userId) {
		ShoppingCart cart = shoppingCartService.getCartForUser(userId);
		return ResponseEntity.ok(cart);
	}
	
	//Get all cart
	@GetMapping("/carts/all")
	List<ShoppingCart> getAllCart() {
		return shoppingCartRepository.findAll();
	}
}
