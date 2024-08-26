import { Component, OnInit } from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../../../services/book.service';
import { CommonModule, CurrencyPipe, NgFor, NgIf } from '@angular/common';
import { LoadingSpinnerComponent } from '../../../../shared/components/loading-spinner/loading-spinner.component';
import { AdminHeaderComponent } from '../admin-header/admin-header.component';

@Component({
  selector: 'app-manage-book',
  standalone: true,
  imports: [
    NgIf,
    NgFor,
    CurrencyPipe,
    CommonModule,
    ReactiveFormsModule,
    LoadingSpinnerComponent,
    AdminHeaderComponent,
  ],
  templateUrl: './manage-book.component.html',
  styleUrl: './manage-book.component.css',
})
export class ManageBookComponent implements OnInit {
  bookForm: FormGroup;
  bookId: any;
  coverImagePreview: string | ArrayBuffer | null = null;

  constructor(
    private route: ActivatedRoute,
    private bookService: BookService,
    private router: Router
  ) {
    this.bookForm = new FormGroup({
      bookTitle: new FormControl('', Validators.required),
      author: new FormControl('', Validators.required),
      price: new FormControl('', Validators.required),
      pageCount: new FormControl('', Validators.required),
      description: new FormControl(''),
      coverImage: new FormControl(''),
    });
  }

  ngOnInit(): void {
    this.bookId = this.route.snapshot.params['id'];
    this.bookService.getBookById(this.bookId).subscribe((data) => {
      this.bookForm.patchValue(data);
      this.coverImagePreview = `data:image/png;base64,${data.coverImage}`;
    });
  }

  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length) {
      const file = input.files[0];
      const reader = new FileReader();
      reader.onload = () => {
        this.coverImagePreview = reader.result;
        this.bookForm.patchValue({
          coverImage: reader.result?.toString().split(',')[1],
        });
      };
      reader.readAsDataURL(file);
    }
  }

  onSave(): void {
    if (this.bookForm.valid) {
      this.bookService
        .updateBook(this.bookId, this.bookForm.value)
        .subscribe(() => {
          this.router.navigate(['/admin/dashboard']);
        });
    }
  }

  onCancel(): void {
    this.router.navigate(['/admin/dashboard']);
  }
}
