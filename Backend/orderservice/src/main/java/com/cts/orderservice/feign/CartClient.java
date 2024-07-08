package com.cts.orderservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.orderservice.dto.CartDTO;

@FeignClient(name = "CART-SERVICE")
public interface CartClient {

	@GetMapping("/cart/user/{userId}")
    CartDTO getCartByUserId(@PathVariable("userId") Long userId);
	
	@DeleteMapping("/cart/user/{userId}/clear")
    void clearCart(@PathVariable("userId") Long userId);
}
