package com.example.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cart_items")
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "shopping_cart_id", referencedColumnName = "shopping_cart_id")
	@JsonIgnore
	private ShoppingCart shoppingCart;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "food_function_id", referencedColumnName = "food_function_id")
	private FoodFunction foodFunction;
	private int quantity;
}