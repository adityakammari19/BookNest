package com.cts.cartservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<Cart> addToCart(@PathVariable Long userId, @RequestBody CartItem cartItem) {
        return new ResponseEntity<>(cartService.addToCart(userId, cartItem.getBookId(), cartItem.getQuantity()),HttpStatus.CREATED);
    }
	
	@GetMapping("/book/quantity")
    public  ResponseEntity<Integer> getBookQuantityFromCart(@RequestParam Long userId, @RequestParam Long bookId) {
        return ResponseEntity.ok(cartService.getBookQuantityFromCart(userId, bookId));
    }
	
	 @PostMapping("/book/quantity/increment")
	    public ResponseEntity<Cart> incrementCartQuantity(@RequestParam Long userId, @RequestParam Long bookId) {
		 System.out.println(userId);
	        return ResponseEntity.ok(cartService.incrementCartQuantity(userId, bookId));
	    }
	 
	    @PostMapping("/book/quantity/decrement")
	    public ResponseEntity<Cart> decrementCartQuantity(@RequestParam Long userId, @RequestParam Long bookId) {
	        return ResponseEntity.ok(cartService.decrementCartQuantity(userId, bookId));
	    }
	
	@DeleteMapping("/book/removeFromCart")
    public ResponseEntity<Map<String, String>> removeFromCart(@RequestParam Long userId, @RequestParam Long bookId) {
        cartService.removeFromCart(userId, bookId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully Deleted CartItem with id:"+bookId+" and User with id:"+userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@GetMapping("/book/isInCart")
    public ResponseEntity<CartItem> isBookInCart(@RequestParam Long userId, @RequestParam Long bookId) {
        CartItem cartItem = cartService.findCartItemByUserIdAndBookId(userId, bookId);
        if (cartItem != null) {
            return ResponseEntity.ok(cartItem);
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/user/{userId}/clearCart")
    public ResponseEntity<Map<String, String>> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully Deleted Cart with User id:" + userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
