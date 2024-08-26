import { Routes } from '@angular/router';
import { BookListComponent } from './core/components/book-list/book-list.component';
import { BookDetailComponent } from './core/components/book-detail/book-detail.component';
import { CartComponent } from './core/components/cart/cart.component';
import { CheckoutComponent } from './core/components/checkout/checkout.component';
import { LoginComponent } from './core/components/auth/login/login.component';
import { RegisterComponent } from './core/components/auth/register/register.component';
import { DashboardComponent } from './core/components/admin/dashboard/dashboard.component';
import { AddBookComponent } from './core/components/admin/add-book/add-book.component';
import { ManageBookComponent } from './core/components/admin/manage-book/manage-book.component';
import { AboutComponent } from './core/components/about/about.component';
import { HomeComponent } from './core/components/home/home.component';
import { OrderHistoryComponent } from './core/components/order-history/order-history.component';
import { AuthGuard } from './core/security/auth.guard';
import { RoleGuard } from './core/security/role.guard';
import { AdminRegisterComponent } from './core/components/admin/admin-register/admin-register.component';
import { ProfileComponent } from './core/components/profile/profile.component';
import { PageNotFoundComponent } from './core/components/page-not-found/page-not-found.component';

export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [
      { path: 'about', component: AboutComponent },
      { path: 'cart', component: CartComponent },
      { path: 'checkout', component: CheckoutComponent },
      { path: 'orders-history', component: OrderHistoryComponent },
      { path: 'bookdetailed/:id', component: BookDetailComponent },
      { path: 'books', component: BookListComponent },
      { path: 'profile', component: ProfileComponent },
    ],
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['ROLE_USER'] },
  },

  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'admin/register', component: AdminRegisterComponent },
  {
    path: 'admin/dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['ROLE_ADMIN'] },
  },
  {
    path: 'admin/add-book',
    component: AddBookComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['ROLE_ADMIN'] },
  },
  {
    path: 'admin/edit-profile',
    component: ProfileComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['ROLE_ADMIN'] },
  },
  {
    path: 'admin/manage-book/:id',
    component: ManageBookComponent,
    canActivate: [AuthGuard, RoleGuard],
    data: { roles: ['ROLE_ADMIN'] },
  },
  { path: '**', component: PageNotFoundComponent, canActivate: [AuthGuard] },
];
