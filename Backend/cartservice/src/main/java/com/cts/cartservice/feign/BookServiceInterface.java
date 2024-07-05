package com.cts.cartservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.cartservice.dto.BookDTO;

@FeignClient("BOOK-SERVICE")
public interface BookServiceInterface {
	
	@GetMapping("/books/book/{bookId}")
    public BookDTO getBookById(@PathVariable("bookId") Long bookId);

}
