package com.cts.cartservice.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cts.cartservice.dto.BookDTO;
import com.cts.cartservice.dto.UserDTO;
import com.cts.cartservice.feign.BookServiceInterface;
import com.cts.cartservice.feign.UserServiceInterface;
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
	 private UserServiceInterface userClient;

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
	public Cart addToCart(Long userId, Long bookId, int quantity) {
		Cart cart = getCartByUserId(userId);
		if (cart == null) {
			cart = new Cart();
			cart.setUserId(userId);
			cart.setCartItems(new ArrayList<>());
		}
		
		
		// Fetch book details
        BookDTO book = bookClient.getBookById(bookId);
        
        CartItem cartItem = new CartItem(bookId, quantity);
        cartItem.setBook(book);
        cart.addCartItem(cartItem);
        cartRepository.save(cart);
        return cart;
        
 
//        BookDTO book = bookClient.getBookById(bookId);
//        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
//            .filter(item -> item.getBookId().equals(bookId))
//            .findFirst();
// 
//        if (existingCartItem.isPresent()) {
//            existingCartItem.get().setQuantity(existingCartItem.get().getQuantity() + 1);
//        } else {
//            CartItem cartItem = new CartItem();
//            cartItem.setBookId(book.getBookId());
//            cartItem.setQuantity(1);
//            cartItem.setBook(book);
//            cart.getCartItems().add(cartItem);
//        }
//        cartRepository.save(cart);
//        return cart;
	}

	@Override
	public void removeFromCart(Long userId, Long bookId) {
		Cart cart = getCartByUserId(userId);
        cart.getCartItems().removeIf(cartItem -> cartItem.getBookId().equals(bookId));
        cartRepository.save(cart);

	}
	@Override
	public int getBookQuantityFromCart(Long userId, Long bookId) {
		Cart cart = getCartByUserId(userId);
	
		if(cart != null) {
			Optional<CartItem> cartItem =  cart.getCartItems().stream()
		            .filter(item -> item.getBookId().equals(bookId))
		            .findFirst();
			if(cartItem.isPresent()) {
				return cartItem.get().getQuantity();
			}
		}
		return 0;
		
	}
	
	public Cart incrementCartQuantity(Long userId, Long bookId) {
        Cart cart = getCartByUserId(userId);
        if (cart != null) {
            cart.getCartItems().forEach(cartItem -> {
                if (cartItem.getBookId().equals(bookId)) {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                }
            });
            return cartRepository.save(cart);
        }
        return null;
    }
 
    public Cart decrementCartQuantity(Long userId, Long bookId) {
        Cart cart = getCartByUserId(userId);
        if (cart != null) {
            cart.getCartItems().forEach(cartItem -> {
                if (cartItem.getBookId().equals(bookId) && cartItem.getQuantity() > 1) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                }
            });
            return cartRepository.save(cart);
        }
        return null;
    }

	@Override
	public void clearCart(Long userId) {
		Cart cart = getCartByUserId(userId);
        cart.getCartItems().clear();
        cartRepository.save(cart);

	}
	
	@Override
	public CartItem findCartItemByUserIdAndBookId(Long userId, Long bookId) {
        Cart cart = getCartByUserId(userId);
        if (cart != null) {
            return cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getBookId().equals(bookId))
                .findFirst()
                .orElse(null);
        }
        return null;
	}

	@Override
	public UserDTO getUserById(Long userId) {
		 return userClient.getUserById(userId);
	}

}
