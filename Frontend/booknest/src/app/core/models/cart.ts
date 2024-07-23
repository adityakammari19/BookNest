export interface Cart {
    id: number;
    userId: number;
    items: CartItem[];
    totalAmount: number;
  }


  export interface CartItem {
    bookId: number;
    quantity: number;
  }