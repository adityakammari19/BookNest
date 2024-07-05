package com.cts.cartservice.model;

import org.springframework.stereotype.Component;

import com.cts.cartservice.dto.BookDTO;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
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
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
	@JsonBackReference
    private Cart cart;
	private Long bookId;
	private int quantity;
	
	@Transient
    private BookDTO book;
	
	public CartItem(Long bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

}
