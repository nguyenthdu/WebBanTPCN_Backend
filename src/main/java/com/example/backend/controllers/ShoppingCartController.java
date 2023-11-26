package com.example.backend.controllers;

import com.example.backend.config.UserAuthenticationProvider;
import com.example.backend.dtos.ErrorDto;
import com.example.backend.dtos.FoodFunctionDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.entities.ShoppingCart;
import com.example.backend.exceptions.AppException;
import com.example.backend.repositories.FoodFunctionRepository;
import com.example.backend.repositories.ShoppingCartRepository;
import com.example.backend.services.FoodFunctionService;
import com.example.backend.services.ShoppingCartService;
import com.example.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ShoppingCartController {
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	private final ShoppingCartService shoppingCartService;
	private FoodFunctionRepository foodFunctionRepository;
	private final FoodFunctionService foodFunctionService;
	private final UserService userService;
	private final UserAuthenticationProvider userAuthenticationProvider;
	
	// Thêm sản phẩm vào giỏ hàng
	@PostMapping("/carts")
	public ResponseEntity<ErrorDto> addFoodToCart(@RequestParam("FoodId") Long id,
	                                              @RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
	                                              Principal principal) {
		FoodFunctionDto foodFunctionDto = foodFunctionService.findById(id);
		if(principal == null) {
			return ResponseEntity.badRequest().body(new ErrorDto("You must login to add food to cart", HttpStatus.BAD_REQUEST.value(), Instant.now().toString()));
		}
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if(authentication != null && authentication.getPrincipal() instanceof UserDto) {
				UserDto userDto = (UserDto) authentication.getPrincipal();
				ShoppingCart shoppingCart = shoppingCartService.addFoodToCart(foodFunctionDto, quantity, userDto.getUsername());
			}
		} catch (AppException e) {
			// Xử lý ngoại lệ khi thêm sản phẩm không thành công
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Add food to cart successfully with id: " + id + " - with quantity: " + quantity, HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	// Xóa sản phẩm khỏi giỏ hàng
	@DeleteMapping("/carts")
	public ResponseEntity<ErrorDto> removeFoodFromCart(@RequestParam("FoodId") Long id,
	                                                   Principal principal) {
		FoodFunctionDto foodFunctionDto = foodFunctionService.findById(id);
		if(principal == null) {
			return ResponseEntity.badRequest().body(new ErrorDto("You must login to remove food from cart", HttpStatus.BAD_REQUEST.value(), Instant.now().toString()));
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try {
			if(authentication != null && authentication.getPrincipal() instanceof UserDto) {
				UserDto userDto = (UserDto) authentication.getPrincipal();
				ShoppingCart shoppingCart = shoppingCartService.removeFoodFromCart(foodFunctionDto, userDto.getUsername());
			}
		} catch (AppException e) {
			// Xử lý ngoại lệ khi xóa sản phẩm không thành công
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Remove food from cart successfully  with id: " + id, HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	// Cập nhật số lượng sản phẩm trong giỏ hàng
	@PutMapping("/carts")
	public ResponseEntity<ErrorDto> updateCartItemQuantity(
			@RequestParam("FoodId") Long id,
			@RequestParam(value = "quantity", required = false, defaultValue = "1") int quantity,
			Principal principal) {
		FoodFunctionDto foodFunctionDto = foodFunctionService.findById(id);
		if(principal == null) {
			return ResponseEntity.badRequest().body(new ErrorDto("You must login to update food quantity in cart", HttpStatus.BAD_REQUEST.value(), Instant.now().toString()));
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try {
			if(authentication != null && authentication.getPrincipal() instanceof UserDto) {
				UserDto userDto = (UserDto) authentication.getPrincipal();
				ShoppingCart shoppingCart = shoppingCartService.updateCartItemQuantity(foodFunctionDto, quantity, userDto.getUsername());
			}
		} catch (AppException e) {
			// Xử lý ngoại lệ khi cập nhật số lượng sản phẩm không thành công
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
		return ResponseEntity.ok(new ErrorDto("Update cart item quantity successfully with id: " + id + " - with quantity " + quantity, HttpStatus.OK.value(), Instant.now().toString()));
	}
	
	@GetMapping("/carts")
	public ResponseEntity<ShoppingCart> getCart(Principal principal) {
		if(principal == null) {
			return ResponseEntity.badRequest().body(new ShoppingCart());
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.getPrincipal() instanceof UserDto) {
			UserDto userDto = (UserDto) authentication.getPrincipal();
			ShoppingCart cart = shoppingCartService.getCartByUsername(userDto.getUsername());
			return ResponseEntity.ok(cart);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
//	@DeleteMapping("/carts")
//	public ResponseEntity<ErrorDto> removeFoodFromCart(@RequestParam Long shoppingCartId, @RequestParam Long foodFunctionId) {
//		try {
//			shoppingCartService.removeFoodFromCart(shoppingCartId, foodFunctionId);
//		} catch (AppException e) {
//			// Xử lý ngoại lệ khi xóa sản phẩm không thành công
//			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
//		}
//		return ResponseEntity.ok(new ErrorDto("Remove food from cart successfully  with id: " + foodFunctionId, HttpStatus.OK.value(), Instant.now().toString()));
//	}
//	@PutMapping("/carts")
//	public ResponseEntity<ErrorDto> updateCartItemQuantity(@RequestParam Long cartItemId, @RequestParam int quantity) {
//		try {
//			shoppingCartService.updateCartItemQuantity(cartItemId, quantity);
//		} catch (AppException e) {
//			// Xử lý ngoại lệ khi cập nhật số lượng sản phẩm không thành công
//			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
//		}
//		return ResponseEntity.ok(new ErrorDto("Update cart item quantity successfully with id: " + cartItemId + "with quantity " + quantity, HttpStatus.OK.value(), Instant.now().toString()));
//	}
	// Lấy thông tin giỏ hàng
//	@GetMapping("/carts")
//	public ResponseEntity<ShoppingCart> getCart(@RequestParam Long userId) {
//		ShoppingCart cart = shoppingCartService.getCartForUser(userId);
//		return ResponseEntity.ok(cart);
//	}
	
	//Get all cart
	@GetMapping("/carts/all")
	List<ShoppingCart> getAllCart() {
		return shoppingCartRepository.findAll();
	}
}