import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-admin-header',
  standalone: true,
  imports: [NgIf, RouterLink, RouterLinkActive],
  templateUrl: './admin-header.component.html',
  styleUrl: './admin-header.component.css',
})
export class AdminHeaderComponent {
  constructor(public authService: AuthService, private router: Router) {}

  onAddBook(): void {
    this.router.navigate(['/admin/add-book']);
  }

  onEditProfile(): void {
    this.router.navigate(['/admin/edit-profile']);
  }

  logout(): void {
    this.authService.logout();
  }
}
