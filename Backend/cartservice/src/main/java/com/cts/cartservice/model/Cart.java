package com.cts.cartservice.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @ToString
@Component
public class Cart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long cartId;
	
	private Long userId;
	
//	@ElementCollection
//	private List<Long> cartItemId;
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> cartItems = new ArrayList<>();
	
	
	public Cart(Long userId) {
        this.userId = userId;
    }
	
	public void addCartItem(CartItem cartItem) {
		cartItems.add(cartItem);
		cartItem.setCart(this);
    }

    public void removecartItem(CartItem cartItem) {
    	cartItems.remove(cartItem);
    	cartItem.setCart(null);
    }

}
