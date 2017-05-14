import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { CustomerDetailComponent } from './customer/customer-detail.component';
import { LoginComponent } from './login.component';
import { OrderDetailComponent } from './orders/order-detail.component';
import { OrderMakeComponent } from './orders/order-make.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'customer',  component: CustomerDetailComponent },
  { path: 'orders', component: OrderDetailComponent },
  { path: 'order/make', component: OrderMakeComponent }
];
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
