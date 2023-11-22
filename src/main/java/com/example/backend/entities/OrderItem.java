package com.example.backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id", nullable = false)
	private Order_ order;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "food_function_id", nullable = false)
	private FoodFunction foodFunction;
	private int quantity;
}
