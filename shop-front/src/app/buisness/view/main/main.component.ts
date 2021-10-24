import {Component, OnInit} from '@angular/core';
import {Product} from "../../model/product";
import {Category} from "../../model/category";
import {ProductService} from "../../data/dao/impl/ProductService";
import {CategoryService} from "../../data/dao/impl/CategoryService";
import {AuthService} from "../../../auth/service/auth.service";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  isLoading: boolean = false;

  menuOpened: boolean;
  showBackdrop: boolean;
  menuMode: string;
  menuPosition: string;

  products: Product[];
  categories: Category[];

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private authService: AuthService) {

    this.productService.findAll().subscribe(res => this.products = res);

  }

  ngOnInit(): void {
    this.initSidebar();
  }

  initSidebar(): void {
    this.menuPosition = 'right';

    // настройки бокового меню
    this.menuOpened = false;
    this.menuMode = 'open';
    this.showBackdrop = false;
  }


  toggleSidebar() {
    this.menuOpened = !this.menuOpened;
  }
}
