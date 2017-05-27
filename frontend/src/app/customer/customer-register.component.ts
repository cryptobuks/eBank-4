import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { Customer } from './customer';
import { CustomerService } from './customer.service';

@Component({
  selector: 'customer-register',
  templateUrl: './customer-register.component.html',
  styleUrls: ['./customer-register.component.css'],
  providers: [CustomerService]
})
export class CustomerRegisterComponent {

  customer: Customer;
	error: string;

  constructor(private customerService: CustomerService, private router: Router) {
    this.customer = new Customer();
  }

  register() {
    console.log(this.customer);
    this.customerService.registerCustomer(this.customer).subscribe(res => {
      console.log(res);
      this.router.navigate(["/login"]);
    }, err => {
			this.error = err.json().message;
		});
  }

  print(a: string){
    console.log(a);
  }

}
