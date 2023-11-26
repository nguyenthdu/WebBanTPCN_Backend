package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "shopping_cart_id")
	private Long id;
	@OneToOne
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User_ user;
	@OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
	private Set<CartItem> cartItems;
	private int totalItem;
	private double totalPrice;
	private int discount;
	
	public ShoppingCart() {
		this.cartItems = new HashSet<>();
		this.totalItem = 0;
		this.totalPrice = 0;
	}
}
