import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Category} from "../../../../model/category";
import {ProductSearchValues} from "../../../../data/dao/search/SearchObjects";
import {AbstractControl, FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-product-filter',
  templateUrl: './product-filter.component.html',
  styleUrls: ['./product-filter.component.css']
})
export class ProductFilterComponent implements OnInit {
  @Output()
  filterEvent = new EventEmitter<ProductSearchValues>()

  maxPrice: number;
  minPrice: number;
  categoryIds: number[] = [];

  productTitle: string;
  form: FormGroup;
  productSearch: ProductSearchValues;

  categories: Category[];
  firstSubmitted = false;

  @Input('categories')
  set setCategories(categories: Category[]) {
    this.categories = categories;
  }

  @Input('productSearchValues')
  set setProductSearchValues(productSearchValues: ProductSearchValues) {
    this.productSearch = productSearchValues;
  }


  constructor(private formBuilder: FormBuilder) {
  }

  get minPriceField(): AbstractControl {
    return this.form.get('minPrice');
  }

  get maxPriceField(): AbstractControl {
    return this.form.get('maxPrice');
  }

  get titleField(): AbstractControl {
    return this.form.get('productTitle');
  }

  get categoryField(): AbstractControl {
    return this.form.get('minPrice');
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
        minPrice: [''],
        maxPrice: [''],
        productTitle: ['']
      }
    );
  }

  submitForm() {
    this.firstSubmitted = true;

    if (this.form.invalid) {
      return;
    }
    if (this.checkFilters()) {
      this.filterEvent.emit(this.productSearch);
    }
  }

  resetFormValues() {
    this.productSearch.isFiltered = false;
    this.productSearch.minPrice = 0;
    this.productSearch.maxPrice = 99999999999;
    this.productSearch.title = '';

    this.filterEvent.emit(this.productSearch);
  }

  checkFilters(): boolean {
    var changed = false;
    if (this.productSearch.minPrice !== this.minPriceField.value) {
      this.productSearch.minPrice = this.minPriceField.value;
      changed = true;
    }
    if (this.productSearch.maxPrice !== this.maxPriceField.value) {
      this.productSearch.maxPrice = this.maxPriceField.value;
      changed = true;
    }
    if (this.productSearch.title !== this.titleField.value) {
      this.productSearch.title = this.titleField.value;
      changed = true;
    }

    if (this.productSearch.categoryIds !== this.categoryIds) {
      this.productSearch.categoryIds = this.categoryIds;
      changed = true;
    }

    this.productSearch.isFiltered = changed;
    return changed;
  }

  onChange(id: number, checked: boolean) {
    if (checked) {
      this.categoryIds.push(id);
    } else {
      let index = this.categoryIds.indexOf(id);
      this.categoryIds.splice(index, 1);
    }
  }
}
