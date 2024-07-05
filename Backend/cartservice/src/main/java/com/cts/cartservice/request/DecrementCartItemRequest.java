package com.cts.cartservice.request;

import lombok.Data;

@Data
public class DecrementCartItemRequest {

	private Long cartItemId;

    private int quantity;
}
