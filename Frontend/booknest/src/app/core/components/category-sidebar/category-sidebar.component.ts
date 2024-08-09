import { NgFor } from '@angular/common';
import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { BookService } from '../../services/book.service';

@Component({
  selector: 'app-category-sidebar',
  standalone: true,
  imports: [NgFor],
  templateUrl: './category-sidebar.component.html',
  styleUrl: './category-sidebar.component.css',
})
export class CategorySidebarComponent implements OnInit {
  categories: string[] = [];
  @Output() categorySelected = new EventEmitter<string>();

  constructor(private bookService: BookService) {}

  ngOnInit(): void {
    this.bookService.getCategories().subscribe((categories) => {
      this.categories = categories;
    });
  }

  selectCategory(category: string): void {
    this.categorySelected.emit(category);
    // console.log(category);
  }
}
