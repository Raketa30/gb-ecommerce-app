import {Component, OnInit} from '@angular/core';
import {CartService} from "../../../data/dao/impl/CartService";
import {Product} from "../../../model/product";

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {
  productsMap: Map<Product, number>;

  constructor(private cartService: CartService
  ) {
  }

  ngOnInit(): void {
    this.productsMap = this.cartService.getProductMap();
  }

  getProductMap(): Map<Product, number> {
    return this.productsMap;
  }

  getProductList(): IterableIterator<Product> {
    return this.productsMap.keys()
  }

  getProductQuantity(product: Product): number {
    return this.productsMap.get(product);
  }

  getTotalPrice(product: Product): number {
    return product.cost * this.productsMap.get(product);
  }

  clearCart() {
    this.cartService.clearCart();
    this.cartService.resetCartItemCount();
  }

  getTotalCartPrice(): number {
    let total = 0;
    for (let product of this.productsMap.keys()) {
      total += this.getTotalPrice(product)
    }
    return total;
  }

}
