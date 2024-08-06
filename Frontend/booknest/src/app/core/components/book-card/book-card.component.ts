import { Component, Input, OnInit } from '@angular/core';
import { Book } from '../../models/book';
import { CartService } from '../../services/cart.service';
import { Router } from '@angular/router';
import { CurrencyPipe, NgIf } from '@angular/common';
import { CartItem } from '../../models/cart';

@Component({
  selector: 'app-book-card',
  standalone: true,
  imports: [CurrencyPipe, NgIf],
  templateUrl: './book-card.component.html',
  styleUrl: './book-card.component.css',
})
export class BookCardComponent implements OnInit {
  @Input() book!: Book;
  userId: number = 1;
  inCart = false;
  cartQuantity = 0;

  constructor(private cartService: CartService, private router: Router) {}

  ngOnInit(): void {
    this.cartService
      .isBookInCart(this.userId, this.book.bookId)
      .subscribe((cartItem) => {
        if (cartItem) {
          this.inCart = true;
          this.cartQuantity = cartItem.quantity;
        }
      });
  }

  addToCart(book: Book): void {
    const cartItem: CartItem = { bookId: book.bookId, quantity: 1 };
    this.cartService.addToCart(1, cartItem).subscribe(() => {
      this.inCart = true;
      this.cartQuantity = 1;
      alert('Book added to cart');
    });
  }

  incrementCartQuantity(): void {
    this.cartService
      .incrementCartQuantity(this.userId, this.book.bookId)
      .subscribe(() => {
        this.cartQuantity++;
      });
  }

  decrementCartQuantity(): void {
    if (this.cartQuantity > 1) {
      this.cartService
        .decrementCartQuantity(this.userId, this.book.bookId)
        .subscribe(() => {
          this.cartQuantity--;
        });
    }
  }

  viewDetails(book: Book): void {
    this.router.navigate(['/books', book.bookId]);
  }

  getCoverImage(coverImage: string): string {
    return `data:image/jpeg;base64,${coverImage}`;
  }
}
