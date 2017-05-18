import { Component, OnInit } from '@angular/core';

import { Customer } from '../customer/customer';
import { CustomerService } from '../customer/customer.service';

@Component({
  selector: 'admin-users',
  templateUrl: './admin-users.component.html',
  providers: [CustomerService]
})
export class AdminUsersComponent implements OnInit {

  customers: Customer[];
  selectedCustomer: Customer;
  error: string;

  constructor(private customerService: CustomerService) {

  }

  ngOnInit() {
    this.customerService.getCustomers().subscribe(res => {
      this.customers = res;
    });
  }

  setEditableCustomer(customer: Customer) {
    this.selectedCustomer = customer;
  }

  editCustomer(customer: Customer) {
    this.customerService.editCustomer(customer).subscribe(res => {
      console.log("edited");
  }, err => {
      this.error = err.json().error;
      console.log(err.json());
    });
  }

}
