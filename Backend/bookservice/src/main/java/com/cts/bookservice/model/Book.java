package com.cts.bookservice.model;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter @AllArgsConstructor
@NoArgsConstructor @ToString
@Component
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long bookId;
	
	private String bookTitle;
	private String author;
	private String description;
	private String isbn;
	private int pageCount;
	private String category;
	private Double price;
	
	@Lob
	@Column(columnDefinition = "mediumblob")
	private String coverImage;
	
	

}
