import {Component, OnInit} from '@angular/core';
import {Category} from "../../../model/category";
import {CategoryService} from "../../../data/dao/impl/CategoryService";

@Component({
  selector: 'app-category-view',
  templateUrl: './category-view.component.html',
  styleUrls: ['./category-view.component.css']
})
export class CategoryViewComponent implements OnInit {

  categories: Category[];

  constructor(private categoryService: CategoryService) {

  }

  ngOnInit(): void {
    this.categoryService.getCategoryList().subscribe(
      result => {
        this.categories = result;
      }
    )
  }

}
