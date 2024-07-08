package com.cts.orderservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @ToString
public class CartDTO {
	private Long userId;
    private List<CartItemDTO> cartItems;

}
