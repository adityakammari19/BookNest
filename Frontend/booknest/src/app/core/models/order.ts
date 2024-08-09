import { Book } from './book';

export interface Order {
  id: number;
  userId: number;
  orderItems: OrderItem[];
  totalAmount: number;
  orderDate: Date;
}

export interface OrderItem {
  bookId: number;
  quantity: number;
  book: Book;
}
