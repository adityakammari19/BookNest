import { Book } from "./book";

export interface Cart {
    id: number;
    userId: number;
    cartItems: CartItemResponse[];
    // totalAmount: number;
  }


  export interface CartItem {
    bookId: number;
    quantity: number;
  }
  export interface CartItemResponse {
    bookId: number;
    quantity: number;
    book: Book
  }