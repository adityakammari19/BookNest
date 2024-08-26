import { Component, OnInit } from '@angular/core';
import {
  ActivatedRoute,
  NavigationEnd,
  Router,
  RouterOutlet,
} from '@angular/router';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { BookListComponent } from '../book-list/book-list.component';
import { CategorySidebarComponent } from '../category-sidebar/category-sidebar.component';
import { NgIf } from '@angular/common';
import { BookService } from '../../services/book.service';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';
import { AuthService } from '../../services/auth.service';

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
    LoadingSpinnerComponent,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  books: any[] = [];
  isLoading = false;

  constructor(
    public router: Router,
    private route: ActivatedRoute,
    private bookService: BookService,
    private authService: AuthService
  ) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        if (event.urlAfterRedirects === '/') {
          this.loadAllBooks();
        }
      }
    });
  }

  ngOnInit(): void {
    const currentUser = this.authService.currentUserValue;

    if (currentUser) {
      if (currentUser.role === 'ROLE_ADMIN') {
        this.router.navigate(['/admin/dashboard']);
      } else if (currentUser.role === 'ROLE_USER') {
        this.router.navigate(['/']);
      }
    } else {
      this.router.navigate(['/login']);
    }

    this.route.queryParams.subscribe((params) => {
      const category = params['category'];
      if (category) {
        this.loadBooksByCategory(category);
      } else {
        this.loadAllBooks();
      }
    });
  }

  loadAllBooks(): void {
    this.isLoading = true;
    this.bookService.getBooks().subscribe((books) => {
      this.books = books;
      this.isLoading = false;
    });
  }

  loadBooksByCategory(category: string): void {
    this.isLoading = true;
    this.bookService.getBooksByCategory(category).subscribe((books) => {
      this.books = books;
      this.isLoading = false;
    });
  }

  onCategorySelected(category: string): void {
    this.router.navigate(['/books'], { queryParams: { category } });
  }
}
// export class HomeComponent implements OnInit {
//   books: any[] = [];
//   isLoading = false;

//   constructor(public router: Router, private bookService: BookService) {
//     this.router.events.subscribe((event) => {
//       if (event instanceof NavigationEnd && event.urlAfterRedirects === '/') {
//         this.loadALlBooks();
//       }
//     });
//   }

//   ngOnInit(): void {
//     this.loadALlBooks();
//   }

//   loadALlBooks(): void {
//     this.isLoading = true;
//     this.bookService.getBooks().subscribe((books) => {
//       this.books = books;
//       this.isLoading = false;
//     });
//   }

//   onCategorySelected(category: string): void {
//     this.bookService.getBooksByCategory(category).subscribe((books) => {
//       this.books = books;
//     });
//   }
// }
