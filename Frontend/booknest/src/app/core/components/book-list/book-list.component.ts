import { Component, OnInit } from '@angular/core';
import { Book } from '../../models/book';
import { BookService } from '../../services/book.service';
import { BookCardComponent } from '../book-card/book-card.component';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [BookCardComponent],
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.css'
})
export class BookListComponent implements OnInit {
  books: Book[] = [];
  book: Book | undefined;
 
  constructor(private bookService: BookService) { }
 
  ngOnInit(): void {
    this.bookService.getBooks().subscribe(books => {
      this.books = books;
      console.log(books)
    });
  }

  
}
