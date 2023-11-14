package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToOne
	@JoinColumn(name = "user_id")
	private User_ user;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CartItem> cartItems = new HashSet<>();
	private int totalItem;
	private double totalPrice;
	private int discount;
}
