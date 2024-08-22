import { Component, OnInit } from '@angular/core';
import { Book } from '../../models/book';
import { BookService } from '../../services/book.service';
import { CartService } from '../../services/cart.service';
import { ActivatedRoute } from '@angular/router';
import { CurrencyPipe, NgIf } from '@angular/common';
import { CartItem } from '../../models/cart';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-book-detail',
  standalone: true,
  imports: [CurrencyPipe, NgIf],
  templateUrl: './book-detail.component.html',
  styleUrl: './book-detail.component.css',
})
export class BookDetailComponent implements OnInit {
  bookId?: number;
  book?: Book;
  // userId: number;
  userId: number = this.userService.getUserIdfromLocalStorage();
  inCart = false;
  cartQuantity = 0;

  constructor(
    private route: ActivatedRoute,
    private bookService: BookService,
    private userService: UserService,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.bookId = Number(params.get('id'));
      if (this.bookId) {
        this.bookService.getBookById(this.bookId).subscribe((book) => {
          this.book = book;
        });
        this.cartService
          .isBookInCart(this.userId, this.bookId)
          .subscribe((cartItem) => {
            if (cartItem) {
              this.inCart = true;
              this.cartQuantity = cartItem.quantity;
            }
          });
      }
    });
  }

  addToCart(book: Book): void {
    const cartItem: CartItem = { bookId: book?.bookId!, quantity: 1 };
    this.cartService.addToCart(this.userId, cartItem).subscribe(() => {
      this.inCart = true;
      this.cartQuantity = 1;
    });
  }

  incrementCartQuantity(): void {
    this.cartService
      .incrementCartQuantity(this.userId, this.book!.bookId)
      .subscribe(() => {
        this.cartQuantity++;
      });
  }

  decrementCartQuantity(): void {
    if (this.cartQuantity > 1) {
      this.cartService
        .decrementCartQuantity(this.userId, this.book!.bookId)
        .subscribe(() => {
          this.cartQuantity--;
        });
    }
  }

  getCoverImage(coverImage: string | undefined): string {
    return `data:image/jpeg;base64,${coverImage}`;
  }
}
