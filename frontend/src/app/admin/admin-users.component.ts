import { Component, OnInit } from '@angular/core';

import { Customer } from '../customer/customer';
import { CustomerService } from '../customer/customer.service';

@Component({
  selector: 'admin-users',
  templateUrl: './admin-users.component.html',
	styleUrls: ['./admin-users.component.css'],
  providers: [CustomerService]
})
export class AdminUsersComponent implements OnInit {

  customers: Customer[];
  selectedCustomer: Customer;
  error: string;

  page: number;
  limit: number;

  totalPages: number[];

  constructor(private customerService: CustomerService) {
    this.page = 1;
    this.limit = 1;
  }

  ngOnInit() {
    this.customerService.getCustomers(this.page, this.limit).subscribe(res => {
      this.customers = res.content;
      this.totalPages = Array(res.totalPages).fill(0).map((i, x) => x + 1);
      console.log(res);
    });
  }

  setEditableCustomer(customer: Customer) {
    this.selectedCustomer = customer;
  }

  editCustomer(customer: Customer) {
    this.customerService.editCustomer(customer).subscribe(res => {
			console.log(customer);
      console.log("edited");
  }, err => {
      this.error = err.json().error;
      console.log(err.json());
    });
  }

  setPage(page: number) {
    this.page = page;
    this.customerService.getCustomers(page, this.limit).subscribe(res => {
      this.customers = res.content;
    })
  }

}
