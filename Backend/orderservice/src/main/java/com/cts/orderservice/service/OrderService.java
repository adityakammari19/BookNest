package com.cts.orderservice.service;

import java.util.List;

import com.cts.orderservice.model.Order;

public interface OrderService {

	public Order createOrder(Long userId) ;
	 public List<Order> getOrdersByUserId(Long userId);
	 public Order getOrderById(Long orderId);
	
}
