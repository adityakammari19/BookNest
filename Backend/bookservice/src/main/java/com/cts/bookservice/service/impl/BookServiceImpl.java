package com.cts.bookservice.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cts.bookservice.exception.BookNotFoundException;
import com.cts.bookservice.model.Book;
import com.cts.bookservice.repository.BookRepository;
import com.cts.bookservice.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Override
	public Book addBook(Book book) {
		return bookRepository.save(book);
	}
//	@Override
//	public Book addBook(String bookTitle,String description,String author,String category, String isbn, int pageCount,Double price) {
//		Book tempBook = new Book();
//		tempBook.setAuthor(author);
//		tempBook.setBookTitle(bookTitle);
//		tempBook.setCategory(category);
//		tempBook.setDescription(description);
//		tempBook.setIsbn(isbn);
//		tempBook.setPageCount(pageCount);
//		tempBook.setPrice(price);
//		
//		return bookRepository.save(tempBook);
//	}

	@Override
	public List<Book> findByCategory(String category) {
		return bookRepository.findByCategory(category);
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Book getBookById(Long id) {
		return bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException("Book not found with id " + id));
	}

	@Override
	public Book getBookByIsbn(String isbn) {
		return bookRepository.findByIsbn(isbn);
	}

	@Override
	public void deleteBook(Long id) throws BookNotFoundException {
		bookRepository.findById(id)
				.orElseThrow(() -> new BookNotFoundException("Book not found with id " + id));
		bookRepository.deleteById(id);
	}

}
