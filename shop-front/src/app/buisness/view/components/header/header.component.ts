import {Component, OnInit} from '@angular/core';
import {AuthService, User} from "../../../../auth/service/auth.service";
import {CartService} from "../../../data/dao/impl/CartService";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  user: User = null;
  count: number = 0;

  constructor(private authService: AuthService,
              private cartService: CartService
  ) {
    this.authService.currentUser.subscribe(
      result => {
        this.user = result;
      }
    );

    this.cartService.cartItemsCount.subscribe(
      res => {
        this.count = res;
      }
    )
  }

  ngOnInit(): void {
  }

  isAdmin() {
    for (let role of this.user.roles) {
      if (role.name == 'ROLE_ADMIN') {
        return this.isLogged()
      }
    }
    return false;
  }

  isLogged() {
    return this.user !== null;
  }

  isManager() {
    for (let role of this.user.roles) {
      if (role.name == 'ROLE_MANAGER') {
        return this.isLogged()
      }
    }
    return false;
  }
}
