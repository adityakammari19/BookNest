import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../../services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-register',
  standalone: true,

  templateUrl: './admin-register.component.html',
  styleUrl: './admin-register.component.css',
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
})
export class AdminRegisterComponent implements OnInit {
  registerForm: FormGroup;
  errorMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(5)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(4)]],
      firstName: ['', [Validators.required, Validators.minLength(5)]],
      lastName: ['', Validators.required],
      phoneNumber: [
        '',
        [Validators.required, Validators.pattern('^[1-9][0-9]{9}$')],
      ],
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    if (this.registerForm.valid) {
      const { username, email, password, firstName, lastName, phoneNumber } =
        this.registerForm.value;
      this.authService
        .adminRegister({
          username,
          email,
          password,
          firstName,
          lastName,
          phoneNumber,
        })
        .subscribe(
          (response) => {
            this.router.navigate(['/login']);
          },
          (error) => {
            console.error('Registration failed:', error);
            if (error.status === 409) {
              this.errorMessage = 'Username or email already exists.';
            } else {
              this.errorMessage = 'Registration failed. Please try again.';
            }
          }
        );
    }
  }
}
