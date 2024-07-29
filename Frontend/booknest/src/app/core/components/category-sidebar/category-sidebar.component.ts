import { NgFor } from '@angular/common';
import { Component } from '@angular/core';

@Component({
  selector: 'app-category-sidebar',
  standalone: true,
  imports: [NgFor],
  templateUrl: './category-sidebar.component.html',
  styleUrl: './category-sidebar.component.css'
})
export class CategorySidebarComponent {
  categories:string[] = ["Fantasy","Sci-Fi","Grafic Novel"]
  

  onCategoryClick(category:string){

  }

}
