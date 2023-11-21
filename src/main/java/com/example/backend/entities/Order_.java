package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Order_ {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	private User_ user;
	//	@ToString.Exclude
//	@EqualsAndHashCode.Exclude
//	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//	private Set<CartItem> cartItems = new HashSet<>();
	@Column(name = "order_date")
	private LocalDate orderDate;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", nullable = false)
	private ShippingAddress shippingAddress;
	@Column(name = "total_price")
	private double totalPrice;
	@Column(name = "total_item")
	private int totalItem;
	private int discount;
}
