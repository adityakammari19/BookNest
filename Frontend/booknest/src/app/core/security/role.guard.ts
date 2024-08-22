import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const currentUser = this.authService.currentUserValue;

    if (currentUser) {
      const roles = route.data['roles'] as Array<string>;
      if (roles && roles.includes(currentUser.role)) {
        return true;
      } else {
        // Redirect based on role
        if (currentUser.role === 'ROLE_ADMIN') {
          this.router.navigate(['/admin/dashboard']);
        } else if (currentUser.role === 'ROLE_USER') {
          this.router.navigate(['/']);
        }
        return false;
      }
    }

    this.router.navigate(['/login']); // Redirect to login if no user is found
    return false;
  }
}
