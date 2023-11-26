package com.example.backend.controllers;

import com.example.backend.dtos.ErrorDto;
import com.example.backend.dtos.UserDto;
import com.example.backend.entities.Order_;
import com.example.backend.entities.ShippingAddress;
import com.example.backend.entities.ShoppingCart;
import com.example.backend.exceptions.AppException;
import com.example.backend.services.OrderService;
import com.example.backend.services.ShippingAddressService;
import com.example.backend.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.Instant;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class OrderController {
	private final ShoppingCartService shoppingCartService;
	private final OrderService orderService;
	private final ShippingAddressService shippingAddressService;
	
	//TODO: checkout
	// Checkout và tạo đơn hàng mới
	@PostMapping("/checkout")
	public ResponseEntity<ErrorDto> checkout(@RequestParam("`shippingAddressId`") Long shippingAddressId, Principal principal) {
		try {
			if(principal != null) {
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				if(authentication != null && authentication.getPrincipal() instanceof UserDto) {
					UserDto userDto = (UserDto) authentication.getPrincipal();
					ShoppingCart shoppingCart = shoppingCartService.getCartByUsername(userDto.getUsername());
					// Lấy đối tượng ShippingAddress từ ID
					ShippingAddress shippingAddress = shippingAddressService.findShippingAddressById(shippingAddressId);
					Order_ order = orderService.save(shoppingCart, shippingAddress);
					// Bạn có thể thực hiện bất kỳ xử lý nào khác cần thiết ở đây (ví dụ: thanh toán, gửi email xác nhận, vv.)
					return ResponseEntity.ok(new ErrorDto("Order placed successfully with order ID: " + order.getId(), HttpStatus.OK.value(), Instant.now().toString()));
				}
			}
			return ResponseEntity.badRequest().body(new ErrorDto("You must login to checkout", HttpStatus.BAD_REQUEST.value(), Instant.now().toString()));
		} catch (AppException e) {
			// Xử lý ngoại lệ khi checkout không thành công
			return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), e.getStatus(), e.getTimestamp()));
		}
	}
}
