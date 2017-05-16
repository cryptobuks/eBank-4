import { Component, OnInit } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Rx';

import { Customer } from './customer/customer';
import { Creditor } from './creditor/creditor';
import { Order } from './orders/order';
import { CustomerService } from './customer/customer.service';

import { AuthService } from './auth.service';
import { AuthGuard } from './auth-guard.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  providers: [CustomerService]
})
export class AppComponent implements OnInit {

  customer: Customer;



  constructor(private http: Http, private customerService: CustomerService, private router: Router,
     private authService: AuthService) {

  }

  logout() {
    this.authService.logout();
  }

  ngOnInit() {
    this.authService.isAuthenticated().subscribe(res => {
      this.authService.isLoggedIn = true;
      this.authService.role = res.json().authorities[0].authority;
    }, err => {
      this.authService.isLoggedIn = false;
    });
  }

}
