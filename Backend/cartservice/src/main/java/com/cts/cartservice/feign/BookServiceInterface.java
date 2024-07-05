package com.cts.cartservice.feign;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.cartservice.dto.BookDTO;

public interface BookServiceInterface {
	
	@GetMapping("/books/{bookId}")
    public BookDTO getBookById(@PathVariable("bookId") Long bookId);

}
