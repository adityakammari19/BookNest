import { Component, Input, OnInit } from '@angular/core';
import { Book } from '../../models/book';
import { BookService } from '../../services/book.service';
import { BookCardComponent } from '../book-card/book-card.component';
import { NgFor } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [BookCardComponent, NgFor],
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.css',
})
export class BookListComponent implements OnInit {
  @Input() books: Book[] = [];
  book: Book | undefined;

  constructor(
    private route: ActivatedRoute,
    private bookService: BookService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      const category = params['category'];
      const search = params['search'];

      if (search) {
        this.bookService
          .searchBooksByTitleOrAuthor(search)
          .subscribe((books) => {
            this.books = books;
          });
      } else if (category) {
        this.bookService.getBooksByCategory(category).subscribe((books) => {
          this.books = books;
        });
      } else {
        this.bookService.getBooks().subscribe((books) => {
          this.books = books;
        });
      }
    });
  }
}
