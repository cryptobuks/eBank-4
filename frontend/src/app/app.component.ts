import { Component } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Rx';

import { Customer } from './customer/customer';
import { Creditor } from './creditor/creditor';
import { Order } from './orders/order';
import { CustomerService } from './customer/customer.service';

import { AuthService } from './auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [CustomerService]
})
export class AppComponent {

  customer: Customer;
  loggedIn: boolean;
  //
  constructor(private http: Http, private customerService: CustomerService, private router: Router,
     private authService: AuthService) {
  //   let headers = new Headers();
  //   headers.append('X-Requested-With', 'XMLHttpRequest');
  //   this.http.get('/api/customer', {headers}).subscribe(test => {
  //     console.log(test);
  //     this.loggedIn = true;
  //     console.log("wow");
  //   }, error => {
  //     this.loggedIn = false;
  //     console.log("errrr");
  //   });
    // this.authService.getUser().subscribe(res => {
    //   console.log("WTF");
    // });
  }
  //
  // // createAuthorizationHeader(headers: Headers) {
  // //   headers.append('Authorization', 'Basic ' + btoa('stanojovskib@gmail.com:12345'));
  // // }
  //
  // get() {
  //   this.customerService.getCustomer().subscribe(res => {
  //     this.customer = res;
  //     console.log(res);
  //   });
  // }
  //
  // logout() {
  //   console.log()
  //   this.http.post('/api/logout', {}).subscribe(test => {
  //     console.log(test);
  //     this.loggedIn = false;
  //   });
  // }
  //


  //
  // customer: Customer;
  // creditor: Creditor;
  // orders: Order[];
  //
  // constructor() {
  //
  //
  //   this.customer = new Customer('{"id":1,"firstName":"Stefan","lastName":"Kondinski","embg":"1234512345123","address":"mukos","email":"kondinskis@gmail.com","transactionNumber":"1234123412341234","role":"ADMIN","balance":1423.0,"active":true}');
  //   this.creditor = new Creditor('{"id":1,"name":"ANHOCH","address":"KARPOS","transactionNumber":"1234123443211234"}');
  //   this.orders = [];
  //   console.log(this.customer);
  //   console.log(this.creditor);
  //
  //   var items = JSON.parse('[{"id":1,"date":1449874800000,"amount":8000.0,"customer":{"id":2,"firstName":"Bojan","lastName":"Stanojovski","embg":"1234123412343","address":"Dimce","email":"stanojovskib@gmail.com","transactionNumber":"1234123412341235","role":"ADMIN","balance":43211.0,"active":true},"creditor":{"id":1,"name":"ANHOCH","address":"KARPOS","transactionNumber":"1234123443211234"},"description":"KUPI GRAFIKA GTX1050TI"}]');
  //
  //   items.map((item) => this.orders.push(item));
  //   console.log(this.orders);
  // }

}
