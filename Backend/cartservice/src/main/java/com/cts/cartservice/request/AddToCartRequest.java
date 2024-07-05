package com.cts.cartservice.request;


import lombok.Data;

@Data
public class AddToCartRequest {
	
    private Long bookId;

    
    private int quantity;

}
