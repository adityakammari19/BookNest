// import { HttpClient } from '@angular/common/http';
// import { Injectable } from '@angular/core';
// import { Router } from '@angular/router';
// import { BehaviorSubject, Observable } from 'rxjs';
// import { JwtHelperService } from '@auth0/angular-jwt';

// @Injectable({
//   providedIn: 'root',
// })
// export class AuthService {
//   private apiUrl = 'http://localhost:8765/api/auth';
//   private currentUserSubject: BehaviorSubject<any>;

//   constructor(
//     private http: HttpClient,
//     private router: Router,
//     public jwtHelper: JwtHelperService
//   ) {
//     this.currentUserSubject = new BehaviorSubject<any>(
//       localStorage.getItem('currentUser')
//     );
//   }

//   public get currentUserValue(): any {
//     return this.currentUserSubject.value;
//   }

//   login(credentials: { username: string; password: string }) {
//     return this.http.post(`${this.apiUrl}/login`, credentials).subscribe(
//       (response: any) => {
//         if (response && response.accessToken) {
//           localStorage.setItem('currentUser', response.accessToken);
//           console.log(response.accessToken);
//           this.currentUserSubject.next(response.accessToken);
//           this.router.navigate(['/']);
//         }
//       },
//       (error) => {
//         console.error('Login failed:', error);
//         // Handle the error (e.g., display a message to the user)
//       }
//     );
//   }
//   // login(credentials: { username: string; password: string }): Observable<any> {
//   //   return this.http.post(`${this.apiUrl}/login`, credentials);
//   // }

//   logout() {
//     localStorage.removeItem('currentUser');
//     this.currentUserSubject.next(null);
//     this.router.navigate(['/login']);
//   }

//   register(newUser: {
//     username: string;
//     email: string;
//     password: string;
//     firstName: string;
//     lastName: string;
//     phoneNumber: string;
//   }): Observable<any> {
//     return this.http.post(`${this.apiUrl}/register`, newUser);
//   }

//   public isAuthenticated(): boolean {
//     const token = localStorage.getItem('currentUser');
//     return !this.jwtHelper.isTokenExpired(token);
//   }
// }
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { UserService } from './user.service'; // Import UserService
import { CartService } from './cart.service';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8765/api/auth';
  private currentUserSubject: BehaviorSubject<any>;

  constructor(
    private http: HttpClient,
    private router: Router,
    public jwtHelper: JwtHelperService,
    private userService: UserService, // Inject UserService
    private cartService: CartService // Inject UserService
  ) {
    const storedUser = localStorage.getItem('currentUser');
    this.currentUserSubject = new BehaviorSubject<any>(
      storedUser ? JSON.parse(storedUser) : null
    );
  }

  public get currentUserValue(): any {
    return this.currentUserSubject.value;
  }

  login(credentials: { username: string; password: string }) {
    return this.http.post(`${this.apiUrl}/login`, credentials).subscribe(
      (response: any) => {
        if (response && response.accessToken) {
          const token = response.accessToken;
          // localStorage.setItem('currentUser', token);
          // console.log(token);

          // Extract username from token payload
          const decodedToken = this.jwtHelper.decodeToken(token);
          const username = decodedToken.sub;
          const role = decodedToken.role;
          console.log(username, role);
          const currentUser = { token, username, role };
          localStorage.setItem('currentUser', JSON.stringify(currentUser));
          this.currentUserSubject.next(currentUser);

          // Retrieve userId using username
          this.userService.getUserByUsername(username).subscribe((user) => {
            const userId = user.userId;
            const currentUser = { token, username, userId, role };

            localStorage.setItem('currentUser', JSON.stringify(currentUser));
            localStorage.setItem('userId', userId.toString());

            this.currentUserSubject.next(currentUser);

            this.cartService.getCart(userId).subscribe((cart) => {
              this.router.navigate(['/']);
            });
          });
        }
      },
      (error) => {
        console.error('Login failed:', error);
        // Handle the error (e.g., display a message to the user)
      }
    );
  }

  logout() {
    localStorage.removeItem('currentUser');
    localStorage.removeItem('userId');
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

  adminRegister(newUser: {
    username: string;
    email: string;
    password: string;
    firstName: string;
    lastName: string;
    phoneNumber: string;
  }): Observable<any> {
    return this.http.post(`${this.apiUrl}/admin/register`, newUser);
  }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('currentUser');
    return !this.jwtHelper.isTokenExpired(token);
  }
}
