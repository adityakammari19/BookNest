package com.cts.cartservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/cart/")
@AllArgsConstructor
public class CartController {
	private CartService cartService;

	@GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
		Cart cart =cartService.getCartByUserId(userId);
		cart.getCartItems().forEach(item -> {
            BookDTO book = cartService.getBookById(item.getBookId());
            item.setBook(book);
        });
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

	@PostMapping("/user/{userId}")
    public ResponseEntity<CartItem> addToCart(@PathVariable Long userId, @RequestBody CartItem cartItem) {
        return new ResponseEntity<>(cartService.addToCart(userId, cartItem.getBookId(), cartItem.getQuantity()),HttpStatus.CREATED);
    }
	
	@DeleteMapping("/user/{userId}/item/{cartItemId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long userId, @PathVariable Long cartItemId) {
        cartService.removeFromCart(userId, cartItemId);
        return new ResponseEntity<>("Successfully Deleted CartItem with id:"+cartItemId+" and User with id:"+userId,HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}/clear")
    public ResponseEntity<String> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return new ResponseEntity<>("Successfully Deleted Cart with User id:"+userId,HttpStatus.OK);
        
    }
}
