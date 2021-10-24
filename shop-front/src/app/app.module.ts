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
import {ProductComponent} from './buisness/view/page/product/product.component';
import {HeaderComponent} from './buisness/view/page/header/header.component';
import {MainComponent} from './buisness/view/page/main/main.component';
import {CartComponent} from './buisness/view/page/cart/cart.component';
import {environment} from "../environments/environment";
import {PRODUCT_URL_TOKEN} from "./buisness/data/dao/impl/ProductService";
import {CATEGORY_URL_TOKEN} from "./buisness/data/dao/impl/CategoryService";
import {SidebarModule} from "ng-sidebar";
import {ProductFilterComponent} from './buisness/view/page/filter/product-filter/product-filter.component';
import {MatIconModule} from "@angular/material/icon";
import {MatButtonModule} from "@angular/material/button";
import {AddProductComponent} from './buisness/view/dialog/add-product/add-product.component';
import {AddCategoryComponent} from './buisness/view/dialog/add-category/add-category.component';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MatPaginatorModule} from "@angular/material/paginator";

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
    ProductFilterComponent,
    AddProductComponent,
    AddCategoryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    SidebarModule,
    MatIconModule,
    MatButtonModule,
    MatPaginatorModule
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: RequestInterceptor, multi: true},

    {
      provide: PRODUCT_URL_TOKEN,
      useValue: environment.backendURL + environment.apiVersion + 'product'
    },
    {
      provide: CATEGORY_URL_TOKEN,
      useValue: environment.backendURL + environment.apiVersion + 'category'
    },
    {provide: MAT_DIALOG_DATA, useValue: {}},
    {provide: MatDialogRef, useValue: {}}
  ],
  entryComponents: [
    AddProductComponent,
    AddCategoryComponent,
  ],

  bootstrap: [AppComponent]
})
export class AppModule { }
