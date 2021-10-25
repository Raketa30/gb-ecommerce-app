import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ProductService} from "../../../data/dao/impl/ProductService";
import {Product} from "../../../model/product";
import {CartService} from "../../../data/dao/impl/CartService";

@Component({
  selector: 'app-item-category',
  templateUrl: './item-category.component.html',
  styleUrls: ['./item-category.component.css']
})
export class ItemCategoryComponent implements OnInit {
  id: string;

  products: Product[];

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private cartService: CartService
  ) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.id = params.id;
      this.productService.findByCategoryId(this.id).subscribe(
        result => {
          this.products = result;
        }
      );
    });
  }

  addToCart(product: Product): void {
    this.cartService.addProduct(product);
  }

}
