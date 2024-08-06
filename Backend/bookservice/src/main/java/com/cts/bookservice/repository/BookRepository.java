package com.cts.bookservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cts.bookservice.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
//	List<Book> findByCategory(String category);
	 List<Book> findByCategoriesContaining(String category);
	Book findByIsbn(String isbn);
	
	@Query("SELECT b FROM Book b WHERE lower(b.bookTitle) LIKE lower(concat('%',?1,'%')) OR lower(b.author) LIKE lower(concat('%',?1,'%'))")
	List<Book> searchByBookTitleOrAuthor(String keyword);

}
