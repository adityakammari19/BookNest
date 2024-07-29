import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  imports: [CommonModule, ReactiveFormsModule]
})
export class LoginComponent {

  loginForm: FormGroup;
 
  constructor(private fb: FormBuilder, private userService: UserService, private router: Router) {
this.loginForm = this.fb.group({
email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }
 
  onSubmit(): void {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;
      this.userService.login(email, password).subscribe(() => {
        this.router.navigate(['/']);
      });
    }
  }
}
