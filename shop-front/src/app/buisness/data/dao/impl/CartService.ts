import {Injectable} from '@angular/core';
import {Product} from "../../../model/product";
import {BehaviorSubject} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CartService {
  cartItemsCount = new BehaviorSubject<number>(0);
  private productMap: Map<Product, number>;

  constructor() {
    this.productMap = new Map<Product, number>();
  }

  addProduct(product: Product): void {
    if (this.productMap.has(product)) {
      this.productMap.set(product, this.productMap.get(product) + 1);
    } else {
      this.productMap.set(product, 1);
    }
    this.countItems();
  }

  removeProduct(product: Product): void {
    if (this.productMap.has(product)) {
      this.productMap.delete(product);
    }
    this.countItems();
  }

  private countItems(): void {
    let count = 0;
    for (let value of this.productMap.values()) {
      count += value
    }
    this.cartItemsCount.next(count);
  }

  getProductMap(): Map<Product, number> {
    return this.productMap;
  }


  clearCart() {
    this.productMap.clear();
  }
}
