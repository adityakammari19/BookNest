import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Cart } from '../models/cart';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private baseUrl = 'http://localhost:8765/api/cart';
 
  constructor(private http: HttpClient) { }
 
  getCart(userId: number): Observable<Cart> {
    return this.http.get<Cart>(`${this.baseUrl}/${userId}`);
  }
 
  addToCart(userId: number, bookId: number, quantity: number): Observable<Cart> {
return this.http.post<Cart>(`${this.baseUrl}/add`, { userId, bookId, quantity });
  }
 
  updateCartItem(userId: number, bookId: number, quantity: number): Observable<Cart> {
    return this.http.put<Cart>(`${this.baseUrl}/update`, { userId, bookId, quantity });
  }
 
  removeCartItem(userId: number, bookId: number): Observable<Cart> {
    return this.http.delete<Cart>(`${this.baseUrl}/remove/${userId}/${bookId}`);
  }
 
  clearCart(userId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/clear/${userId}`);
  }
}
