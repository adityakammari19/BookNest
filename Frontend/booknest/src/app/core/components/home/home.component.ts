import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { BookListComponent } from '../book-list/book-list.component';
import { CategorySidebarComponent } from '../category-sidebar/category-sidebar.component';
import { NgIf } from '@angular/common';
import { BookService } from '../../services/book.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    RouterOutlet,
    HeaderComponent,
    FooterComponent,
    BookListComponent,
    CategorySidebarComponent,
    NgIf,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  books: any[] = [];

  constructor(public router: Router, private bookService: BookService) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd && event.urlAfterRedirects === '/') {
        this.loadALlBooks();
      }
    });
  }

  ngOnInit(): void {
    this.loadALlBooks();
  }

  loadALlBooks(): void {
    this.bookService.getBooks().subscribe((books) => {
      this.books = books;
      console.log(books);
    });
  }

  onCategorySelected(category: string): void {
    this.bookService.getBooksByCategory(category).subscribe((books) => {
      this.books = books;
    });
  }
}
