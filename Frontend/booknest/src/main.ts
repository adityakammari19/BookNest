import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { AppComponent } from './app/app.component';
import {
  HTTP_INTERCEPTORS,
  provideHttpClient,
  withInterceptors,
} from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { routes } from './app/app.routes';
import { importProvidersFrom } from '@angular/core';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { JwtModule } from '@auth0/angular-jwt';
import { AuthService } from './app/core/services/auth.service';
import { jwtInterceptor } from './app/core/security/jwt.intercepter';
import { AuthGuard } from './app/core/security/auth.guard';

bootstrapApplication(AppComponent, {
  providers: [
    provideRouter(routes),
    provideHttpClient(withInterceptors([jwtInterceptor])),
    provideAnimationsAsync(),
    importProvidersFrom(
      JwtModule.forRoot({
        config: {
          tokenGetter: () => localStorage.getItem('currentUser'),
        },
      })
    ),
    AuthService,
    AuthGuard,
    // provideRouter(routes),
    // provideHttpClient(),
    // provideAnimationsAsync(),

    // importProvidersFrom(
    //   JwtModule.forRoot({
    //     config: {
    //       tokenGetter: () => localStorage.getItem('currentUser'),
    //       allowedDomains: ['http://localhost:8765'],
    //       disallowedRoutes: [
    //         'http://localhost:8765/api/auth/login',
    //         'http://localhost:8765/api/auth/register',
    //       ],
    //     },
    //   })
    // ),
    // AuthService,
    // { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    // AuthGuard,
  ],
}).catch((err) => console.error(err));
