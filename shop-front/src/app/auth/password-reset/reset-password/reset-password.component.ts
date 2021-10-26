import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  form: FormGroup;
  error: string;
  firstSubmitted: boolean = false;
  isLoading: boolean = false;
  showPasswordForm: boolean = false;
  private token: string;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  get passwordField(): AbstractControl {
    return this.form.get('password');
  }

  get passwordRepeatField(): AbstractControl {
    return this.form.get('passwordRepeat');
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
        password: ['', [Validators.required, Validators.minLength(5)]],
        passwordRepeat: ['', [Validators.required, Validators.minLength(5)]]
      }
    );

    this.route.params.subscribe(params => {
      this.token = params.token;
      this.showPasswordForm = true;
    });
  }

  submitForm(): void {
    this.firstSubmitted = true;

    if (this.form.invalid) {
      return;
    }

    this.isLoading = true;

    this.authService.resetPassword(this.passwordField.value, this.token).subscribe(
      result => {
        this.isLoading = false;

        if (result) {
          this.router.navigate(['/info-page', {msg: 'Password successfully updated'}])
        }
      }, err => {
        this.isLoading = false;
        this.router.navigate(['/info-page', {msg: 'Error with password changing. Try again'}])
      }
    );
  }

}
