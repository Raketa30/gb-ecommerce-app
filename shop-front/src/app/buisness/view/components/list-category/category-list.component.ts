import {Component, Input, OnInit} from '@angular/core';
import {Category} from "../../../model/category";

@Component({
  selector: 'app-category-item',
  templateUrl: './category-list.component.html',
  styleUrls: ['./category-list.component.css']
})
export class CategoryListComponent implements OnInit {

  categories: Category[];

  @Input('categories')
  set setCategories(categories: Category[]) {
    this.categories = categories;
  }

  constructor() {
  }

  ngOnInit(): void {
  }

}
