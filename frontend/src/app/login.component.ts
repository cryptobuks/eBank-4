import { Component, OnInit } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Router } from '@angular/router';

import { Customer } from './customer/customer';
import { CustomerService } from './customer/customer.service';

import { AuthService } from './auth.service';

@Component({
  selector: 'ebank-login',
  templateUrl: './login.component.html',
	styleUrls: ["./login.component.css"],
  providers: [CustomerService]
})
export class LoginComponent implements OnInit {

  customer: Customer;
  credentials: Credentials;
	error: string;

  constructor(private http: Http, private router: Router, private authService: AuthService) {
    this.credentials = new Credentials('', '');
  }

  logIn() {
    this.authService.logIn(this.credentials.username, this.credentials.password).subscribe(res => {
      this.router.navigate(['/customer']);
    }, err => {
      this.router.navigate(['/login']);
			if(err.statusText == 'Unauthorized') {
				this.error = "Bad credentials";
			}
    });
  }

  ngOnInit() {
    this.authService.isAuthenticated().subscribe(res => {
      this.router.navigate(['/customer']);
    }, err => {
      this.router.navigate(['/login']);
    });
  }

  logout() {
    this.authService.logout();
  }

}

class Credentials {

  username: string;
  password: string;

  constructor(username: string, password: string) {

  }
}
