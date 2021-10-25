import {NgModule} from '@angular/core';
import {RouterModule, Routes} from "@angular/router";
import {LoginComponent} from "./auth/login/login.component";
import {RegistrationComponent} from "./auth/registration/registration.component";
import {InfoPageComponent} from "./auth/info-page/info-page.component";
import {ActivateAccountComponent} from "./auth/activate-account/activate-account.component";
import {SendEmailResetPasswordComponent} from "./auth/password-reset/send-email/send-email-reset-password.component";
import {ResetPasswordComponent} from "./auth/password-reset/reset-password/reset-password.component";
import {ProductViewComponent} from "./buisness/view/page/product-view/product-view.component";
import {CartComponent} from "./buisness/view/page/cart/cart.component";
import {WelcomeViewComponent} from "./buisness/view/page/welcome-view/welcome-view.component";
import {CategoryViewComponent} from "./buisness/view/page/category-view/category-view.component";
import {ProductItemComponent} from "./buisness/view/components/product-item/product-item.component";
import {CategoryItemComponent} from "./buisness/view/components/category-item/category-item.component";

// модуль для настройки роутингов

//список роутов
const routes: Routes = [
  // no auth
  {path: '', component: WelcomeViewComponent},
  {path: 'products', component: ProductViewComponent},
  {path: 'category', component: CategoryViewComponent},
  {path: 'product-item/:id', component: ProductItemComponent},
  {path: 'category-item/:id', component: CategoryItemComponent},
  {path: 'cart', component: CartComponent},
  {path: 'login', component: LoginComponent},
  {path: 'logout', redirectTo: '', pathMatch: 'full'},
  {path: 'index', redirectTo: '', pathMatch: 'full'},
  {path: 'register', component: RegistrationComponent, pathMatch: 'full'},
  {path: 'activate-account/:uuid', component: ActivateAccountComponent},
  {path: 'info-page', component: InfoPageComponent},
  {path: 'reset-password', component: SendEmailResetPasswordComponent},
  {path: 'update-password/:token', component: ResetPasswordComponent},
  //with auth
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule {
}
