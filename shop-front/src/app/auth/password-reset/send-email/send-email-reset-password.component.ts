import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-send-email',
  templateUrl: './send-email-reset-password.component.html',
  styleUrls: ['./send-email-reset-password.component.css']
})
export class SendEmailResetPasswordComponent implements OnInit {
  form: FormGroup;
  firstSubmitted: boolean = false;
  isLoading: boolean = false;
  error: string;

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  get emailField(): AbstractControl {
    return this.form.get('email');
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  submitForm(): void {
    this.firstSubmitted = true;

    if (this.form.invalid) {
      return;
    }

    this.isLoading = true;

    this.authService.sendResetPasswordEmail(this.emailField.value).subscribe(
      () => {
        this.isLoading = false;

        this.router.navigate(['/info-page', {msg: 'Password reset email sent! Please check you inbox mail!'}]);
      },

      err => {
        this.isLoading = false;

        switch (err.error.exception) {
          case 'UsernameNotFoundException': {
            this.error = 'User with current email not exist';
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
