import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, RouterLinkActive, CommonModule, ReactiveFormsModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
  searchForm: FormGroup;

  constructor(private router: Router) {
    this.searchForm = new FormGroup({
      search: new FormControl(''),
    });
  }

  onSearch(): void {
    const keyword = this.searchForm.get('search')?.value;
    if (keyword) {
      this.router.navigate(['/books'], { queryParams: { search: keyword } });
    }
  }
}
