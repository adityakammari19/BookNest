package com.cts.cartservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.cartservice.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
//	List<CartItem> findByUserId(Long userId);

}
