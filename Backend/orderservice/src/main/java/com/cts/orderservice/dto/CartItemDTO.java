package com.cts.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @ToString
public class CartItemDTO {
	private Long bookId;
    private int quantity;
    private BookDTO book;

}
