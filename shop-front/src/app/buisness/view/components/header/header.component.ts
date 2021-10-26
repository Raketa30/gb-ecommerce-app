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
  isLoggedOn = false;
  isAdmin = false;
  isUser = true;
  isManager = false;

  constructor(private authService: AuthService,
              private cartService: CartService,
  ) {
    this.cartService.cartItemsCount.subscribe(
      res => {
        this.count = res;
      }
    );
    this.authService.currentUser.subscribe(
      result => {
        this.user = result;
        if (result !== null) {
          this.isLoggedOn = true;
          this.takeRoles();
        }
      }
    )
  }

  ngOnInit(): void {

  }

  logout() {
    this.user = null;
    this.isLoggedOn = false;
    this.isAdmin = false;
    this.isUser = true;
    this.isManager = false;
    this.authService.logout().subscribe(
      (result) => {
        if (result) {
          this.authService.currentUser.next(null);
        }
      }
    )

  }

  private takeRoles(): void {
    for (let role of this.user.roles) {
      if (role.name == 'ROLE_ADMIN') {
        this.isUser = false;
        this.isAdmin = true;
      }
      if (role.name == 'ROLE_MANAGER') {
        this.isUser = false;
        this.isManager = true;
      }
    }
  }
}
