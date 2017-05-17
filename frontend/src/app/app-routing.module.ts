import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { CustomerDetailComponent } from './customer/customer-detail.component';
import { LoginComponent } from './login.component';
import { OrderDetailComponent } from './orders/order-detail.component';
import { OrderMakeComponent } from './orders/order-make.component';
import { CustomerRegisterComponent } from './customer/customer-register.component';
import { AuthGuard } from './auth-guard.service';
import { AuthService } from './auth.service';

import { AdminUsersComponent } from './admin/admin-users.component';
import { AdminOrdersComponent } from './admin/admin-orders.component';

const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'customer',  component: CustomerDetailComponent, canActivate: [AuthGuard] },
  { path: 'order/history', component: OrderDetailComponent, canActivate: [AuthGuard] },
  { path: 'order/make', component: OrderMakeComponent, canActivate: [AuthGuard] },
  { path: 'register', component: CustomerRegisterComponent },
  { path: 'admin/users', component: AdminUsersComponent, canActivate: [AuthGuard] },
  { path: 'admin/orders', component: AdminOrdersComponent, canActivate: [AuthGuard] }
];
@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ],
  providers: [AuthGuard]
})
export class AppRoutingModule {}
