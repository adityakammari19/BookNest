import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { BookService } from '../../../services/book.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AdminHeaderComponent } from '../admin-header/admin-header.component';

@Component({
  selector: 'app-add-book',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, AdminHeaderComponent],
  templateUrl: './add-book.component.html',
  styleUrl: './add-book.component.css',
})
export class AddBookComponent implements OnInit {
  bookForm: FormGroup;
  selectedFile: File | null = null;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private bookService: BookService,
    private router: Router
  ) {
    this.bookForm = this.fb.group({
      bookTitle: ['', Validators.required],
      author: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(1)]],
      pageCount: ['', [Validators.required, Validators.min(1)]],
      isbn: ['', Validators.required],
      description: ['', Validators.required],
      categories: ['', Validators.required],
      coverImage: [''],
    });
  }

  ngOnInit(): void {}

  onFileChange(event: any): void {
    const file = event.target.files[0];
    if (file) {
      this.selectedFile = file;
    }
  }

  onSubmit(): void {
    if (this.bookForm.valid) {
      const formData: FormData = new FormData();
      formData.append('bookTitle', this.bookForm.get('bookTitle')!.value);
      formData.append('author', this.bookForm.get('author')!.value);
      formData.append('price', this.bookForm.get('price')!.value);
      formData.append('pageCount', this.bookForm.get('pageCount')!.value);
      formData.append('isbn', this.bookForm.get('isbn')!.value);
      formData.append('description', this.bookForm.get('description')!.value);
      formData.append(
        'categories',
        this.bookForm
          .get('categories')!
          .value.split(',')
          .map((category: string) => category.trim())
      );
      if (this.selectedFile) {
        formData.append(
          'coverImage',
          this.selectedFile,
          this.selectedFile.name
        );
      }

      this.bookService.addBook(formData).subscribe(
        () => {
          this.router.navigate(['/']);
        },
        (error) => {
          if (error.status === 409) {
            this.errorMessage = 'Book already exists';
          } else {
            this.errorMessage = 'An error occurred. Please try again.';
          }
        }
      );
    }
  }
}
