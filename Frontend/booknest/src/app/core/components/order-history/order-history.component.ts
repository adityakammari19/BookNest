import { Component, OnInit } from '@angular/core';
import { OrderService } from '../../services/order.service';
import { CurrencyPipe, DatePipe, NgFor, NgIf } from '@angular/common';
import { BookService } from '../../services/book.service';
import { OrderItem } from '../../models/order';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';

@Component({
  selector: 'app-order-history',
  standalone: true,
  imports: [CurrencyPipe, NgIf, NgFor, DatePipe, LoadingSpinnerComponent],
  templateUrl: './order-history.component.html',
  styleUrl: './order-history.component.css',
})
export class OrderHistoryComponent implements OnInit {
  userId: number = 1; // Placeholder for logged-in user ID
  orders?: any[] = [];
  isLoading = false;

  constructor(
    private orderService: OrderService,
    private bookService: BookService
  ) {}

  ngOnInit(): void {
    this.loadOrderHistory();
  }

  loadOrderHistory(): void {
    this.isLoading = true;
    this.orderService.getOrdersByUserId(this.userId).subscribe((orders) => {
      this.orders = orders;
      this.loadBooksForOrders();
      this.isLoading = false;
    });
  }

  loadBooksForOrders(): void {
    this.orders?.forEach((order) => {
      order.orderItems.forEach((orderItem: OrderItem) => {
        this.bookService.getBookById(orderItem.bookId).subscribe((book) => {
          orderItem.book = book;
        });
      });
    });
  }
}
