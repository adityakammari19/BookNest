package com.cts.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookDTO {

	private String bookTitle;
	private String author;
	private String description;
	private int pageCount;
	private Double price;
	private String coverImage;
}
