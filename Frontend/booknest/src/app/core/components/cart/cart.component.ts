import { CurrencyPipe, NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Cart } from '../../models/cart';
import { CartService } from '../../services/cart.service';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [NgIf, NgFor, CurrencyPipe],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css',
})
export class CartComponent implements OnInit {
  cart?: Cart;
  userId: number = 1;

  constructor(
    private cartService: CartService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    // const userId = this.authService.getToken(); // assuming the token is the userId for simplicity
    // if (userId) {
    //   this.loadCart(userId);
    // }
    this.loadCart(this.userId);
    // this.cartService.getCart(this.userId).subscribe((cart) => {
    //   this.cart = cart;
    // });
  }

  loadCart(userId: number): void {
    this.cartService.getCart(userId).subscribe((cart) => {
      this.cart = cart;
    });
  }

  incrementFromCart(bookId: number): void {
    // const userId = this.authService.getToken();
    this.cartService
      .incrementCartQuantity(this.userId, bookId)
      .subscribe(() => {
        this.loadCart(this.userId);
      });
  }

  decrementFromCart(bookId: number): void {
    // const userId = this.authService.getToken();
    this.cartService
      .decrementCartQuantity(this.userId, bookId)
      .subscribe(() => {
        this.loadCart(this.userId);
      });
  }
  removeCartItemFromCart(bookId: number): void {
    // const userId = this.authService.getToken();
    this.cartService.removeCartItem(this.userId, bookId).subscribe(() => {
      this.loadCart(this.userId);
    });
  }

  clearCart(): any {
    // const userId = this.authService.getToken();
    this.cartService.clearCart(this.userId).subscribe((msg) => {
      console.log(msg);
      alert(msg.message);
      this.loadCart(this.userId);
    });
  }

  getCoverImage(coverImage: string): string {
    return `data:image/jpeg;base64,${coverImage}`;
  }
}
