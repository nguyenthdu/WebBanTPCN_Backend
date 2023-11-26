package com.example.backend.services.impl;

import com.example.backend.entities.*;
import com.example.backend.repositories.OrderDetailRepository;
import com.example.backend.repositories.OrderRepository;
import com.example.backend.services.OrderService;
import com.example.backend.services.ShippingAddressService;
import com.example.backend.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final OrderDetailRepository orderDetailRepository;
	private final UserServiceImpl userService;
	private final ShoppingCartService shoppingCartService;
	private final ShippingAddressService shippingAddressService;
	
	@Override
	public Order_ save(ShoppingCart shoppingCart, ShippingAddress shippingAddress) {
		Order_ order = new Order_();
		order.setOrderDate(LocalDate.now());
		order.setTotalPrice(shoppingCart.getTotalPrice());
		order.setQuantity(shoppingCart.getTotalItem());
		order.setDiscount(0);
		order.setShippingAddress(shippingAddress);
		order.setUser(shoppingCart.getUser());
		List<OrderDetail> orderDetailList = new ArrayList<>();
		for(CartItem cartItem : shoppingCart.getCartItems()) {
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(order);
			orderDetail.setFoodFunction(cartItem.getFoodFunction());
			orderDetailRepository.save(orderDetail);
			orderDetailList.add(orderDetail);
		}
		order.setOrderDetails(orderDetailList);
		shoppingCartService.clearShoppingCart(shoppingCart.getId());
		return orderRepository.save(order);
	}
	
	@Override
	public List<Order_> findAll(String username) {
		return null;
	}
	
	@Override
	public List<Order_> findAllOrders() {
		return null;
	}
	
	@Override
	public Order_ acceptOrder(Long id) {
		return null;
	}
	
	@Override
	public void cancelOrder(Long id) {
	}
}
