package com.cts.cartservice.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.cartservice.dto.BookDTO;
import com.cts.cartservice.model.Cart;
import com.cts.cartservice.model.CartItem;
import com.cts.cartservice.service.CartService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/cart/")
@AllArgsConstructor
public class CartController {
	private final CartService cartService;

	@GetMapping("/user/{userId}")
    public Cart getCartByUserId(@PathVariable Long userId) {
		Cart cart =cartService.getCartByUserId(userId);
		cart.getCartItems().forEach(item -> {
            BookDTO book = cartService.getBookById(item.getBookId());
            item.setBook(book);
        });
        return cartService.getCartByUserId(userId);
    }

	@PostMapping("/user/{userId}")
    public CartItem addToCart(@PathVariable Long userId, @RequestBody CartItem cartItem) {
        return cartService.addToCart(userId, cartItem.getBookId(), cartItem.getQuantity());
    }
	
	@DeleteMapping("/user/{userId}/item/{cartItemId}")
    public void removeFromCart(@PathVariable Long userId, @PathVariable Long cartItemId) {
        cartService.removeFromCart(userId, cartItemId);
    }

    @DeleteMapping("/user/{userId}/clear")
    public void clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
    }
}
