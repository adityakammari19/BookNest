import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cart, CartItem } from '../models/cart';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private baseUrl = 'http://localhost:8765/api/cart';

  constructor(private http: HttpClient) {}

  getCart(userId: number): Observable<Cart> {
    return this.http.get<Cart>(`${this.baseUrl}/user/${userId}`);
  }

  addToCart(userId: number, cartItem: CartItem): Observable<Cart> {
    return this.http.post<Cart>(`${this.baseUrl}/user/${userId}`, cartItem);
  }

  getBookQuantityFromCart(userId: number, bookId: number): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/book/quantity`, {
      params: {
        userId: userId.toString(),
        bookId: bookId.toString(),
      },
    });
  }

  incrementCartQuantity(userId: number, bookId: number): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/book/quantity/increment?userId=${userId}&bookId=${bookId}`,
      {}
    );
  }

  decrementCartQuantity(userId: number, bookId: number): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/book/quantity/decrement?userId=${userId}&bookId=${bookId}`,
      {}
    );
  }

  removeCartItem(userId: number, bookId: number): Observable<string> {
    return this.http.delete<string>(
      `${this.baseUrl}/book/removeFromCart?userId=${userId}&bookId=${bookId}`,
      {}
    );
  }

  isBookInCart(userId: number, bookId: number): Observable<any> {
    return this.http.get<CartItem>(
      `${this.baseUrl}/book/isInCart?userId=${userId}&bookId=${bookId}`,
      {}
    );
  }

  clearCart(userId: number): Observable<any> {
    return this.http.delete<string>(`${this.baseUrl}/user/${userId}/clearCart`);
  }
}
