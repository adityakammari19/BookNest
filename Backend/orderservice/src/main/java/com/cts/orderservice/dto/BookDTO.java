package com.cts.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @ToString
public class BookDTO {

	private Long id;
    private String title;
    private String author;
    private double price;
}
