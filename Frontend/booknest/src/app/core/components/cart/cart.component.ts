import { CommonModule, CurrencyPipe, NgFor, NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Cart } from '../../models/cart';
import { CartService } from '../../services/cart.service';
import { AuthService } from '../../services/auth.service';

import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { OrderService } from '../../services/order.service';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [
    NgIf,
    NgFor,
    CurrencyPipe,
    CommonModule,
    ReactiveFormsModule,
    LoadingSpinnerComponent,
  ],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css',
})
export class CartComponent implements OnInit {
  cart?: Cart;
  // userId: number = 1;

  totalPrice: number = 0;
  addresses: any[] = [];
  selectedAddress: string = '';
  addressForm: FormGroup;
  newAddressForm: FormGroup;
  showAddNewAddressForm: boolean = false;
  isLoading = false;

  constructor(
    private cartService: CartService,
    private orderService: OrderService,
    private userService: UserService,
    private router: Router,
    private fb: FormBuilder
  ) {
    this.addressForm = this.fb.group({
      selectedAddress: ['', Validators.required],
    });

    this.newAddressForm = this.fb.group({
      houseNumber: ['', Validators.required],
      street: ['', Validators.required],
      city: ['', Validators.required],
      state: ['', Validators.required],
      zip: ['', Validators.required],
      country: ['', Validators.required],
    });
  }
  userId: number = this.userService.getUserIdfromLocalStorage();

  ngOnInit(): void {
    this.loadCart(this.userId);
    this.loadUserAddresses(this.userId);
  }

  loadCart(userId: number): void {
    this.isLoading = true;

    this.cartService.getCart(userId).subscribe((cart) => {
      this.cart = cart;
      this.isLoading = false;

      this.calculateTotalPrice();
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
      alert(msg.message);
      this.loadCart(this.userId);
    });
  }

  //Order and Address part

  calculateTotalPrice(): void {
    this.totalPrice = this.cart!.cartItems.reduce(
      (sum, item) => sum + item.book.price * item.quantity,
      0
    );
  }

  loadUserAddresses(userId: number): void {
    this.userService.getUserAddresses(userId).subscribe((addresses) => {
      this.addresses = addresses;
    });
  }

  toggleAddNewAddressForm(): void {
    this.showAddNewAddressForm = !this.showAddNewAddressForm;
  }

  addNewAddress(): void {
    if (this.newAddressForm.valid) {
      const newAddress = this.newAddressForm.value;
      this.userService.addUserAddress(this.userId, newAddress).subscribe(() => {
        this.loadUserAddresses(this.userId);
        this.selectedAddress = newAddress;
        this.newAddressForm.reset();
        this.showAddNewAddressForm = false;
      });
    }
  }

  placeOrder(): void {
    const orderDetails = {
      userId: this.userId,
      cart: this.cart,
      // totalPrice: this.totalPrice,
      address: this.addressForm.value.selectedAddress,
    };
    this.orderService.placeOrder(orderDetails).subscribe(() => {
      this.cartService.clearCart(this.userId).subscribe(() => {
        this.router.navigate(['/orders-history']);
      });
    });
  }

  getCoverImage(coverImage: string): string {
    return `data:image/jpeg;base64,${coverImage}`;
  }

  viewDetails(bookId: number): void {
    this.router.navigate(['/bookdetailed', bookId]);
  }
}
