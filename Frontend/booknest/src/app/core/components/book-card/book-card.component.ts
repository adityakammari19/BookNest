import { Component, Input } from '@angular/core';
import { Book } from '../../models/book';
import { CartService } from '../../services/cart.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-book-card',
  standalone: true,
  imports: [],
  templateUrl: './book-card.component.html',
  styleUrl: './book-card.component.css'
})
export class BookCardComponent {

  @Input() book!: Book;
 
  constructor(private cartService: CartService, private router: Router) {}
 
  addToCart(book: Book): void {
    const cartItem = { bookId: book.bookId, quantity: 1 };
    // this.cartService.addToCart(cartItem).subscribe(() => {
    //   alert('Book added to cart');
    // });
  }
 
  viewDetails(book: Book): void {
    this.router.navigate(['/books', book.bookId]);
  }

  getCoverImage(coverImage:string ): string{
    return `data:image/jpeg;base64,${coverImage}`
  }
}
