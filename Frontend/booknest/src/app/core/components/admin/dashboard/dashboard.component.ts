import { Component, OnInit } from '@angular/core';
import { BookService } from '../../../services/book.service';
import { Router } from '@angular/router';
import { CommonModule, CurrencyPipe, NgFor, NgIf } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { LoadingSpinnerComponent } from '../../../../shared/components/loading-spinner/loading-spinner.component';
import { AuthService } from '../../../services/auth.service';
import { AdminHeaderComponent } from '../admin-header/admin-header.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [
    NgIf,
    NgFor,
    CurrencyPipe,
    CommonModule,
    ReactiveFormsModule,
    LoadingSpinnerComponent,
    AdminHeaderComponent,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent implements OnInit {
  books: any[] = [];
  selectedBook: any;

  constructor(
    private bookService: BookService,
    private router: Router,
    public authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadBooks();
  }

  loadBooks(): void {
    this.bookService.getBooks().subscribe((data) => {
      this.books = data;
    });
  }

  onEditBook(book: any): void {
    this.selectedBook = book;
    this.router.navigate(['/admin/manage-book', book.bookId]);
  }
}
