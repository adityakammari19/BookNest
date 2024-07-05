package com.cts.cartservice.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @ToString
@Component
public class CartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long cartItemId;
	@ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
	private Long bookId;
	private int quantity;
	private Double totalPrice;

}
