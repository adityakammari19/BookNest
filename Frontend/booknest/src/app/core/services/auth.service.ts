import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8765/api/auth';
  private currentUserSubject: BehaviorSubject<any>;

  constructor(
    private http: HttpClient,
    private router: Router,
    public jwtHelper: JwtHelperService
  ) {
    this.currentUserSubject = new BehaviorSubject<any>(
      localStorage.getItem('currentUser')
    );
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  login(credentials: { username: string; password: string }) {
    return this.http.post(`${this.apiUrl}/login`, credentials).subscribe(
      (response: any) => {
        if (response && response.accessToken) {
          localStorage.setItem('currentUser', response.accessToken);
          console.log(response.accessToken);
          this.currentUserSubject.next(response.accessToken);
          this.router.navigate(['/']);
        }
      },
      (error) => {
        console.error('Login failed:', error);
        // Handle the error (e.g., display a message to the user)
      }
    );
  }
  // login(credentials: { username: string; password: string }): Observable<any> {
  //   return this.http.post(`${this.apiUrl}/login`, credentials);
  // }

  logout() {
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
    this.router.navigate(['/login']);
  }

  register(newUser: {
    username: string;
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    phoneNumber: string;
  }): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, newUser);
  }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('currentUser');
    return !this.jwtHelper.isTokenExpired(token);
  }
}
