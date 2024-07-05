package com.cts.cartservice.service.impl;

import org.springframework.stereotype.Service;

import com.cts.cartservice.dto.BookDTO;
import com.cts.cartservice.feign.BookServiceInterface;
import com.cts.cartservice.model.Cart;
import com.cts.cartservice.model.CartItem;
import com.cts.cartservice.repository.CartRepository;
import com.cts.cartservice.service.CartService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
	private CartRepository cartRepository;
	 private  BookServiceInterface bookClient;

	 public BookDTO getBookById(Long bookId) {
	        return bookClient.getBookById(bookId);
	    }
	 
	@Override
	public Cart getCartByUserId(Long userId) {
		return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart(userId);
            return cartRepository.save(newCart);
        });
	}

	@Override
	public CartItem addToCart(Long userId, Long bookId, int quantity) {
		// Fetch book details
        BookDTO book = bookClient.getBookById(bookId);
        
		Cart cart = getCartByUserId(userId);
        CartItem cartItem = new CartItem(bookId, quantity);
        cartItem.setBook(book);
        cart.addCartItem(cartItem);
        cartRepository.save(cart);
        return cartItem;
	}

	@Override
	public void removeFromCart(Long userId, Long cartItemId) {
		Cart cart = getCartByUserId(userId);
        cart.getCartItems().removeIf(cartItem -> cartItem.getCartItemId().equals(cartItemId));
        cartRepository.save(cart);

	}

	@Override
	public void clearCart(Long userId) {
		Cart cart = getCartByUserId(userId);
        cart.getCartItems().clear();
        cartRepository.save(cart);

	}

}
