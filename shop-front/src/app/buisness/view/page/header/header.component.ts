import {Component, OnInit} from '@angular/core';
import {AuthService, User} from "../../../../auth/service/auth.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  user: User = null;

  constructor(private authService: AuthService) {
    this.authService.currentUser.subscribe(
      result => {
        this.user = result;
      }
    )
  }

  ngOnInit(): void {
  }

}
