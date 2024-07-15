package com.cts.bookservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.bookservice.model.Book;
import com.cts.bookservice.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
	@Autowired
	private BookService bookService;
	
	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {
		return new ResponseEntity<>( bookService.getAllBooks(),HttpStatus.OK);
		
	}
	
	@GetMapping("/book/{bookId}")
	public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
		return new ResponseEntity<>(bookService.getBookById(bookId),HttpStatus.OK);
		
	}
	
	@GetMapping("/categories/{category}")
	public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable String category) {
		return new ResponseEntity<>( bookService.findByCategory(category),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Book> addBook(@RequestBody Book book) {
		return new ResponseEntity<>(bookService.addBook(book),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/book/{bookId}")
	public ResponseEntity<String> deleteBookWithId(@PathVariable Long bookId) {
		bookService.deleteBook(bookId);
		return new ResponseEntity<>("Successfully Deleted book with id:"+bookId,HttpStatus.OK);
		
	}

}
