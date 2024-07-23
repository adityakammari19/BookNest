import { Routes } from '@angular/router';
import { BookListComponent } from './core/components/book-list/book-list.component';
import { BookDetailComponent } from './core/components/book-detail/book-detail.component';
import { CartComponent } from './core/components/cart/cart.component';
import { CheckoutComponent } from './core/components/checkout/checkout.component';
import { OrderSummaryComponent } from './core/components/order-summary/order-summary.component';
import { LoginComponent } from './core/components/auth/login/login.component';
import { RegisterComponent } from './core/components/auth/register/register.component';
import { DashboardComponent } from './core/components/admin/dashboard/dashboard.component';
import { AddBookComponent } from './core/components/admin/add-book/add-book.component';
import { ManageBookComponent } from './core/components/admin/manage-book/manage-book.component';

export const routes: Routes = [
    { path: '', component: BookListComponent },
  { path: 'books/:id', component: BookDetailComponent },
  { path: 'cart', component: CartComponent },
  { path: 'checkout', component: CheckoutComponent },
  { path: 'order-summary', component: OrderSummaryComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'admin', component: DashboardComponent },
  { path: 'admin/add-book', component: AddBookComponent },
  { path: 'admin/manage-books', component: ManageBookComponent }
];
