package com.cts.orderservice.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.orderservice.dto.AddressDTO;
import com.cts.orderservice.dto.CartDTO;
import com.cts.orderservice.dto.OrderRequest;
import com.cts.orderservice.feign.CartClient;
import com.cts.orderservice.model.Order;
import com.cts.orderservice.model.OrderItem;
import com.cts.orderservice.repository.OrderRepository;
import com.cts.orderservice.service.OrderService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
    private CartClient cartClient;

    

    public Order createOrder(Long userId) {
        // Get cart details from cart service
        CartDTO cart = cartClient.getCartByUserId(userId);

        // Convert cart items to order items
        List<OrderItem> orderItems = cart.getCartItems().stream().map(cartItem -> new OrderItem(cartItem.getBookId(), cartItem.getQuantity())).collect(Collectors.toList());

        LocalDate orderDate = LocalDate.now();
        // Calculate total amount
        double totalAmount = cart.getCartItems().stream().mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getBook().getPrice()).sum();
        AddressDTO address = new AddressDTO();
        // Create and save the order
        Order order = new Order(userId,orderDate,orderItems,totalAmount,address);
        orderItems.forEach(orderItem -> orderItem.setOrder(order)); // Set the order reference in each order item
        Order savedOrder = orderRepository.save(order);
        
        cartClient.clearCart(userId);

        return savedOrder;
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findAll().stream().filter(order -> order.getUserId().equals(userId)).collect(Collectors.toList());
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
    
    public Order placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setUserId(orderRequest.getUserId());
        order.setAddress(orderRequest.getAddress());
        order.setOrderDate(LocalDate.now());
//        String address = orderRequest.getAddress();
//        LocalDate orderDate = LocalDate.now();
        Double totalPrice = orderRequest.getCart().getCartItems().stream().mapToDouble(cartItem -> cartItem.getQuantity() * cartItem.getBook().getPrice()).sum();
        order.setTotalPrice(totalPrice);
 
        List<OrderItem> orderItems = orderRequest.getCart().getCartItems().stream()
            .map(cartItem -> new OrderItem(cartItem.getBookId(), cartItem.getQuantity()))
            .collect(Collectors.toList());
    	order.setOrderItems(orderItems);
//        List<OrderItem> orderItems = orderRequest.getItems().stream()
//        		.map(item -> new OrderItem(item.getBook(), item.getQuantity()))
//        		.collect(Collectors.toList());
//        order.setOrderItems(orderItems);
//        Order order = new Order(orderRequest.getUserId(),orderDate, orderItems, totalPrice,address);
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        return orderRepository.save(order);
    }
}
