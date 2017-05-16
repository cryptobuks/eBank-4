import { Component, OnInit } from '@angular/core';

import { Customer } from '../customer/customer';
import { CustomerService } from '../customer/customer.service';

@Component({
  selector: 'admin-test',
  templateUrl: './admin-test.component.html',
  providers: [CustomerService]
})
export class AdminTestComponent implements OnInit {

  customers: Customer[];


  constructor(private customerService: CustomerService) {

  }

  ngOnInit() {
    this.customerService.getCustomers().subscribe(res => {
      this.customers = res;
    });
  }

  editCustomer(customer: Customer) {
    console.log(customer);
  }

}
