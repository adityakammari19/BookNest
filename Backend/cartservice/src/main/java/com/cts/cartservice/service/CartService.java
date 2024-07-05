package com.cts.cartservice.service;

import com.cts.cartservice.dto.BookDTO;
import com.cts.cartservice.dto.UserDTO;
import com.cts.cartservice.model.Cart;
import com.cts.cartservice.model.CartItem;

public interface CartService {
	 public Cart getCartByUserId(Long userId) ;
	 public CartItem addToCart(Long userId, Long bookId, int quantity);
	 public void removeFromCart(Long userId, Long cartItemId);
	 public void clearCart(Long userId);
	 public BookDTO getBookById(Long bookId);
	 public UserDTO getUserById(Long userId);
}
