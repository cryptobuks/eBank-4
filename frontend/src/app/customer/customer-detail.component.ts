import { Component, OnInit, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';

import { CustomerService } from './customer.service';
import { Customer } from './customer';

@Component({
  selector: 'customer-detail',
  templateUrl: './customer-detail.component.html',
  styleUrls: ['./customer-detail.component.css'],
  providers: [CustomerService]
})
export class CustomerDetailComponent implements OnInit {

  @Input() customer: Customer;

  constructor(private customerService: CustomerService, private router: Router, private location: Location) {

  }

  getCustomer(): void {
    this.customerService.getCustomer().subscribe(res => this.customer = res);
  }

  goBack(): void {
    this.location.back();
  }

  ngOnInit(): void {
    this.customerService.getCustomer().subscribe(res => {
      this.customer = res;
    }, err => {
      this.router.navigate(['/login']);
    });
  }

}
