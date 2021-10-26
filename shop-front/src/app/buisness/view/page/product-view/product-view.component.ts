import {Component, OnInit} from '@angular/core';
import {Product} from "../../../model/product";
import {Category} from "../../../model/category";
import {ProductService} from "../../../data/dao/impl/ProductService";
import {CategoryService} from "../../../data/dao/impl/CategoryService";
import {AuthService} from "../../../../auth/service/auth.service";
import {ProductSearchValues} from "../../../data/dao/search/SearchObjects";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-main',
  templateUrl: './product-view.component.html',
  styleUrls: ['./product-view.component.css']
})
export class ProductViewComponent implements OnInit {
  isLoading: boolean = false;

  menuOpened: boolean;
  showBackdrop: boolean;
  menuMode: string;
  menuPosition: string;

  readonly defaultPageSize = 6;
  readonly defaultPageNumber = 0;

  products: Product[];
  categories: Category[];

  productSearch: ProductSearchValues;
  totalProductsFound: number;

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private authService: AuthService) {
    this.productSearch = new ProductSearchValues();

    this.searchProducts(this.productSearch);
  }

  ngOnInit(): void {
    this.initSidebar();
  }

  initSidebar(): void {
    this.menuPosition = 'right';

    // настройки бокового меню
    this.menuOpened = false;
    this.menuMode = 'push';
    this.showBackdrop = false;
  }


  toggleSidebar() {
    this.menuOpened = !this.menuOpened;
  }

  paging(event: PageEvent) {
    if (this.productSearch.pageSize !== event.pageSize) {
      this.productSearch.pageNum = 1;
    } else {
      this.productSearch.pageNum = event.pageIndex;
    }

    this.productSearch.pageSize = event.pageSize;

    this.searchProducts(this.productSearch);
  }

  private searchProducts(productSearch: ProductSearchValues) {
    this.productSearch = productSearch;
    this.productService.findAllPaginated(this.productSearch).subscribe(result => {
      this.products = result['content'];
      this.totalProductsFound = result.totalElements;
      this.searchCategories()
    })
  }

  private searchCategories() {
    this.categoryService.getCategoryList().subscribe(result => {
      console.log(result)
      this.categories = result;
    })
  }

  filterSearch(filterSearch: ProductSearchValues) {
    this.productSearch = filterSearch;
    this.searchProducts(this.productSearch);
  }
}
