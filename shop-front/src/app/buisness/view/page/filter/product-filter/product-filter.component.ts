import {Component, Input, OnInit} from '@angular/core';
import {Category} from "../../../../model/category";
import {ProductSearchValues} from "../../../../data/dao/search/SearchObjects";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrls: ['./product-filter.component.css']
})
export class ProductFilterComponent implements OnInit {

  productTitle: string;
  categories: Category[];
  filterForm: FormGroup;
  private productSearch: ProductSearchValues;

  constructor(private formBuilder: FormBuilder) {
  }

  @Input('categories')
  set setCategories(categories: Category[]) {
    this.categories = categories;
  }

  @Input('productSearchValues')
  set setProductSearchValues(productSearchValues: ProductSearchValues) {
    this.productSearch = productSearchValues;
  }

  ngOnInit(): void {
  }

}
