package com.example.backend.services;

import com.example.backend.entities.Order_;
import com.example.backend.entities.ShippingAddress;
import com.example.backend.entities.ShoppingCart;

import java.util.List;

public interface OrderService {
	Order_ save(ShoppingCart shoppingCart, ShippingAddress shippingAddress);
	
	List<Order_> findAll(String username);
	
	List<Order_> findAllOrders();
	
	Order_ acceptOrder(Long id);
	
	void cancelOrder(Long id);
}
