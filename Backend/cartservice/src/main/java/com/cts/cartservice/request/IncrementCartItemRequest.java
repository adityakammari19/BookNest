package com.cts.cartservice.request;

import lombok.Data;

@Data
public class IncrementCartItemRequest {

    private Long cartItemId;

    private int quantity;
}
