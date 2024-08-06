package com.cts.cartservice.service;

import com.cts.cartservice.dto.BookDTO;
import com.cts.cartservice.dto.UserDTO;
import com.cts.cartservice.model.Cart;
import com.cts.cartservice.model.CartItem;

public interface CartService {
	 public Cart getCartByUserId(Long userId) ;
	 public Cart addToCart(Long userId, Long bookId, int quantity);
	 public void removeFromCart(Long userId, Long bookId);
	 public int getBookQuantityFromCart(Long userId, Long bookId);
	 public Cart incrementCartQuantity(Long userId, Long bookId);
	 public Cart decrementCartQuantity(Long userId, Long bookId);
	 public void clearCart(Long userId);
	 public CartItem findCartItemByUserIdAndBookId(Long userId, Long bookId);
	 public BookDTO getBookById(Long bookId);
	 public UserDTO getUserById(Long userId);
}
