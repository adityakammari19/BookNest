import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { User } from '../../models/user';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';
import { CommonModule, NgIf } from '@angular/common';
import { LoadingSpinnerComponent } from '../../../shared/components/loading-spinner/loading-spinner.component';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [NgIf, CommonModule, ReactiveFormsModule, LoadingSpinnerComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css',
})
export class ProfileComponent {
  profileForm!: FormGroup;
  currentUser: any;
  isEditing: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    this.currentUser = this.authService.currentUserValue;
    this.initializeForm();
    this.loadUserProfile();
  }

  private initializeForm(): void {
    this.profileForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: [
        { value: '', disabled: true },
        [Validators.required, Validators.email],
      ],
      phoneNumber: [''],
    });
  }

  private loadUserProfile(): void {
    this.userService.getUserById(this.currentUser.userId).subscribe({
      next: (user: User) => {
        this.profileForm.patchValue(user);
      },
      error: (err) => {
        console.error('Error loading user profile:', err);
      },
    });
  }

  onEdit(): void {
    this.isEditing = true;
    this.profileForm.enable();
  }

  onSave(): void {
    if (this.profileForm.valid) {
      const updatedUser = { ...this.profileForm.value };
      this.userService
        .updateUser(this.currentUser.userId, updatedUser)
        .subscribe({
          next: () => {
            this.isEditing = false;
            this.profileForm.disable();
            alert('Profile updated successfully!');
          },
          error: (err) => {
            console.error('Error updating profile:', err);
          },
        });
    }
  }

  onCancel(): void {
    this.isEditing = false;
    this.profileForm.patchValue(this.currentUser);
    this.profileForm.disable();
  }
}
