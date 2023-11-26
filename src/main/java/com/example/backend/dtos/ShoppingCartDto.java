package com.example.backend.dtos;

import com.example.backend.entities.User_;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCartDto {
	private Long id;
	private User_ user_;
	private Set<CartItemDto> cartItems;
	private int totalItem;
	private double totalPrice;
	private int discount;
}
