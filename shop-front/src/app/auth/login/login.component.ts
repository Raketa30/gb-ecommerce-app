import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService, User} from "../service/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  user: User;
  error: string;
  firstSubmitted: boolean = false;
  isLoading: boolean = false;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router

  ) {

  }

  get usernameField(): AbstractControl {
    return this.form.get('username');
  }

  get passwordField(): AbstractControl {
    return this.form.get('password');
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
        username: ['', [Validators.required, Validators.minLength(5)]],
        password: ['', [Validators.required, Validators.minLength(5)]]
      }
    );
  }

  submitForm(): void {

    this.firstSubmitted = true;

    if (this.form.invalid) {
      return;
    }

    this.isLoading = true;

    const tmpUser = new User()
    tmpUser.username = this.usernameField.value;
    tmpUser.password = this.passwordField.value;

    this.authService.login(tmpUser).subscribe(
      result => {

        this.isLoading = false;

        this.user = result;
        console.log(result);
        this.authService.isLoggedIn = true;
        this.authService.currentUser.next(this.user);
        this.router.navigate(['/']);
      },

      err => {

        this.isLoading = false;

        switch (err.error.exception) {
          case 'BadCredentialsException' : {
            this.error = 'Wrong username or password';
            break;
          }

          case 'DisabledException' : {
            this.error = 'User not activated';
            break;
          }

          default: {
            this.error = 'Error';
            break;
          }
        }
      }
    );
  }

}
