import { Component, OnInit } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Router } from '@angular/router';

import { Customer } from './customer/customer';
import { CustomerService } from './customer/customer.service';

import { AuthService } from './auth.service';

@Component({
  selector: 'ebank-login',
  templateUrl: './login.component.html',
  providers: [CustomerService]
})
export class LoginComponent implements OnInit {

  customer: Customer;
  loggedIn: boolean;

  constructor(private http: Http, private router: Router, private authService: AuthService) {

  }

  logIn(username: string, password: string) {
    this.authService.logIn(username, password).subscribe(res => {
      this.router.navigate(['/customer']);
    }, err => {
      this.router.navigate(['/login']);
    });
  }

  ngOnInit() {
    this.authService.isAuthenticated().subscribe(res => {
      this.router.navigate(['/customer']);
    }, err => {
      this.router.navigate(['/login']);
    });
  }

}
