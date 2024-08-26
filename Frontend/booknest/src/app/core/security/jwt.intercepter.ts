import { HttpInterceptorFn } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

const EXCLUDED_URLS = [
  'http://localhost:8765/api/auth/login',
  'http://localhost:8765/api/auth/register',
  'http://localhost:8765/api/auth/admin/register',
];

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);

  if (EXCLUDED_URLS.some((url) => req.url.includes(url))) {
    return next(req);
  }

  const currentUser = authService.currentUserValue.token;

  if (currentUser) {
    req = req.clone({
      headers: req.headers.set('Authorization', `Bearer ${currentUser}`),
    });
  }

  return next(req);
};

// @Injectable()
// export class JwtInterceptor implements HttpInterceptor {
//   constructor(private authService: AuthService) {}

//   intercept(
//     request: HttpRequest<any>,
//     next: HttpHandler
//   ): Observable<HttpEvent<any>> {
//     const currentUser = localStorage.getItem('currentUser');
//     console.log(currentUser);
//     request = request.clone({
//       headers: request.headers.set('Authorization', `Bearer ${currentUser}`),
//     });
//     // const currentUser = this.authService.currentUserValue;
//     // if (currentUser) {
//     //   request = request.clone({
//     //     setHeaders: {
//     //       Authorization: `Bearer ${currentUser}`,
//     //     },
//     //   });
//     // }
//     console.log('Request Headers- ' + request.headers);

//     return next.handle(request);
//   }
// }
