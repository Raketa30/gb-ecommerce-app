import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Product} from "../../../model/product";
import {PageEvent} from '@angular/material/paginator';
import {ProductSearchValues} from "../../../data/dao/search/SearchObjects";
import {CartService} from "../../../data/dao/impl/CartService";


@Component({
  selector: 'app-product',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

  @Input('products')
  set setProducts(products: Product[]) {
    this.products = products;
  }

  @Input('totalProductsFounded')
  set setTotalProductsFounded(totalProduct: number) {
    this.totalProductsFounded = totalProduct;
  }

  @Input('productSearchValues')
  set setProductSearchValues(productSearchValues: ProductSearchValues) {
    this.productSearch = productSearchValues;
    this.initSearchValues();
  }

  @Output()
  pagingEvent = new EventEmitter<PageEvent>();

  productSearch: ProductSearchValues;
  totalProductsFounded: number;

  products: Product[];

  constructor(
    private cartService: CartService
  ) {
  }

  ngOnInit(): void {
  }

  pageChanged(pageEvent: PageEvent) {
    this.pagingEvent.emit(pageEvent);
  }

  private initSearchValues() {
    if (!this.productSearch) {
      return;
    }
  }

  addToCart(product: Product) {
    this.cartService.addProduct(product);
  }
}
