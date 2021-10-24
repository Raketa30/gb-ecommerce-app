import {Component, Input, OnInit} from '@angular/core';
import {Product} from "../../model/product";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  products: Product[];

  constructor() {
  }

  @Input('products')
  set setProducts(products: Product[]) {
    this.products = products;
  }

  ngOnInit(): void {
  }

}
