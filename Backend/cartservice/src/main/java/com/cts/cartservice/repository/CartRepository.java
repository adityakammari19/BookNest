package com.cts.cartservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.cartservice.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Optional<Cart>  findByUserId(Long userId);

}
