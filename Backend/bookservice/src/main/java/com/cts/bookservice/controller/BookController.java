package com.cts.bookservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cts.bookservice.dto.UpdateBookDTO;
import com.cts.bookservice.exception.ConflictException;
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
	
	@GetMapping("/categories")
    public ResponseEntity<List<String>> getUniqueCategories() {
        return ResponseEntity.ok(bookService.getUniqueCategories());
    }
	
	@GetMapping("/category/{category}")
	public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable String category) {
		return new ResponseEntity<>( bookService.findByCategory(category),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Book> addBook(@RequestParam("bookTitle") String bookTitle,
            @RequestParam("author") String author,
            @RequestParam("price") Double price,
            @RequestParam("description") String description,
            @RequestParam("isbn") String isbn,
            @RequestParam("categories") List<String> categories,
            @RequestParam("pageCount") int pageCount,
            @RequestParam(value = "coverImage") MultipartFile coverImage) throws ConflictException {
		
		try {
	        Book savedBook = bookService.addBook(bookTitle, description, author, categories, isbn, pageCount, price, coverImage);
	        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
	    } catch (ConflictException e) {
	        return new ResponseEntity<>(null, HttpStatus.CONFLICT);
	    }
	}
	
	@DeleteMapping("/book/{bookId}")
	public ResponseEntity<String> deleteBookWithId(@PathVariable Long bookId) {
		bookService.deleteBook(bookId);
		return new ResponseEntity<>("Successfully Deleted book with id:"+bookId,HttpStatus.OK);
		
	}
	@PutMapping("/book/{bookId}")
	public ResponseEntity<Book> updateBookWithId(@PathVariable Long bookId, @RequestBody UpdateBookDTO book) {
		return  ResponseEntity.ok(bookService.updateBook(bookId,book));
		
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Book>> searchBooksByTitleOrAuthor(@RequestParam String keyword){
		return new ResponseEntity<>(bookService.searchBooksByTitleOrAuthor(keyword),HttpStatus.OK);
	}

}
