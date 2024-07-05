package com.cts.cartservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDTO {

	private Long bookId;
	private String bookTitle;
	private String author;
	private String description;
	private String isbn;
	private int pageCount;
	private String category;
	private Double price;
}
