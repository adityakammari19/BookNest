package com.cts.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @ToString
public class OrderRequest {
	private Long userId;
	private CartDTO cart;
//	private Double totalPrice;
	private AddressDTO address;

}
