import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {LoginComponent} from './auth/login/login.component';
import {AppRoutingModule} from "./app-routing.module";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RegistrationComponent} from './auth/registration/registration.component';
import {InfoPageComponent} from './auth/info-page/info-page.component';
import {ActivateAccountComponent} from './auth/activate-account/activate-account.component';
import {SendEmailResetPasswordComponent} from './auth/password-reset/send-email/send-email-reset-password.component';
import {ResetPasswordComponent} from './auth/password-reset/reset-password/reset-password.component';
import {RequestInterceptor} from "./auth/Interceptor/request-interceptor.service";
import {ProductComponent} from './buisness/product/product.component';
import {HeaderComponent} from './buisness/header/header.component';
import {MainComponent} from './buisness/main/main.component';
import {CartComponent} from './buisness/cart/cart.component';
import {FilterComponent} from './buisness/filter/filter.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegistrationComponent,
    InfoPageComponent,
    ActivateAccountComponent,
    SendEmailResetPasswordComponent,
    ResetPasswordComponent,
    ProductComponent,
    HeaderComponent,
    MainComponent,
    CartComponent,
    FilterComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: RequestInterceptor, multi: true},
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
