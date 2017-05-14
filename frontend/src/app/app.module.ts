import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { CustomerDetailComponent } from './customer/customer-detail.component';
import { OrderDetailComponent } from './orders/order-detail.component';
import { CreditorDetailComponent } from './creditor/creditor-detail.component';
import { LoginComponent } from './login.component';
import { OrderMakeComponent } from './orders/order-make.component';

import { AppRoutingModule } from './app-routing.module';

import { AuthService } from './auth.service';

@NgModule({
  declarations: [
    AppComponent, CustomerDetailComponent, OrderDetailComponent, CreditorDetailComponent,
    LoginComponent, OrderMakeComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    AppRoutingModule
  ],
  providers: [AuthService],
  bootstrap: [AppComponent]
})
export class AppModule { }
