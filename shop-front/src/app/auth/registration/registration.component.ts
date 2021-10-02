import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService, User} from "../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  form: FormGroup;
  error: string;
  firstSubmitted: boolean = false;
  isLoading: boolean = false;

  constructor(private formBuilder: FormBuilder,
              private authService: AuthService,
              private route: ActivatedRoute,
              private router: Router
  ) {
  }

  get usernameField(): AbstractControl {
    return this.form.get('username');
  }

  get emailField(): AbstractControl {
    return this.form.get('email');
  }

  get passwordField(): AbstractControl {
    return this.form.get('password');
  }

  get passwordRepeatField(): AbstractControl {
    return this.form.get('passwordRepeat');
  }

  ngOnInit(): void {

    this.form = this.formBuilder.group({
        username: ['', [Validators.required, Validators.minLength(5)]],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(5)]],
        passwordRepeat: ['', [Validators.required, Validators.minLength(5)]]
      }
    )
  }

  submitForm(): void {
    this.firstSubmitted = true;

    if (this.form.invalid) {
      return;
    }

    this.isLoading = true;

    const user = new User()
    user.username = this.usernameField.value;
    user.email = this.emailField.value;
    user.password = this.passwordField.value;

    this.authService.registration(user).subscribe(
      () => {
        this.isLoading = false;

        this.error = null;

        this.router.navigate(['/info-page', {msg: "We sent confirmation mail. Please check your email address"}])
      },

      err => {
        this.isLoading = false;
        switch (err.error.exception) {
          case 'UserExistsException' : {
            this.error = 'User already exists';
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
