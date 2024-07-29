package com.cts.bookservice.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.cts.bookservice.exception.ConflictException;
import com.cts.bookservice.model.Book;

public interface BookService {
	
//	public Book addBook(Book book);
	public Book addBook(String bookTitle,String description,String author,String category, String isbn, int pageCount,Double price,MultipartFile coverImage) throws ConflictException;
	public List<Book> findByCategory(String category);
	public List<Book> getAllBooks() ;
	public Book getBookById(Long id) ;
	public Book getBookByIsbn(String isbn) ;
	public void deleteBook(Long id);
	
	

}
