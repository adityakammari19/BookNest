package com.cts.cartservice.dto;

import lombok.Data;

@Data
public class CartItemDto {
	
	private Long bookId;
	private int quantity;

}
