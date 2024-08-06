package com.cts.bookservice.service.impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cts.bookservice.exception.BookNotFoundException;
import com.cts.bookservice.exception.ConflictException;
import com.cts.bookservice.model.Book;
import com.cts.bookservice.repository.BookRepository;
import com.cts.bookservice.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;
	
//	@Override
//	public Book addBook(Book book) {
//		return bookRepository.save(book);
//	}
	@Override
	public Book addBook(String bookTitle,String description,String author,List<String> categories, String isbn, int pageCount,Double price,MultipartFile coverImage) throws ConflictException {
		
		if(bookRepository.findByIsbn(isbn) != null) { 
	          throw new ConflictException("Book already exists"); 
	      } 
		
		Book tempBook = new Book();
		
		String coveImageFileName = StringUtils.cleanPath(coverImage.getOriginalFilename());
		
		if(coveImageFileName.contains("..")) {
			System.out.println("CoverImage file is not a valid File");
		}
		
		try {
			tempBook.setCoverImage(Base64.getEncoder().encodeToString(coverImage.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tempBook.setAuthor(author);
		tempBook.setBookTitle(bookTitle);
		tempBook.setCategories(categories);
		tempBook.setDescription(description);
		tempBook.setIsbn(isbn);
		tempBook.setPageCount(pageCount);
		tempBook.setPrice(price);
		
		return bookRepository.save(tempBook);
	}
	
	@Override
	public List<String> getUniqueCategories() {
        List<Book> books = bookRepository.findAll();
        return books.stream()
                .flatMap(book -> book.getCategories().stream())
                .distinct()
                .collect(Collectors.toList());
    }

	@Override
	public List<Book> findByCategory(String category) {
		return bookRepository.findByCategoriesContaining(category);
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

	@Override
	public List<Book> searchBooksByTitleOrAuthor(String keyword) {
		return bookRepository.searchByBookTitleOrAuthor(keyword);
	}

}
