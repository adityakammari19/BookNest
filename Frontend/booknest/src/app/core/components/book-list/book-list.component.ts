import { Component, OnInit } from '@angular/core';
import { Book } from '../../models/book';
import { BookService } from '../../services/book.service';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [],
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

  getCoverImage(coverImage:string ): string{
    return `data:image/jpeg;base64,${coverImage}`
  }
}
