package com.cts.cartservice.service;

import com.cts.cartservice.dto.BookDTO;
import com.cts.cartservice.model.Cart;
import com.cts.cartservice.model.CartItem;

public interface CartService {
	public BookDTO getBookById(Long bookId);
	 public Cart getCartByUserId(Long userId) ;
	 public CartItem addToCart(Long userId, Long bookId, int quantity);
	 public void removeFromCart(Long userId, Long cartItemId);
	 public void clearCart(Long userId);
}
