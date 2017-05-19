import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { OrderService } from './order.service';
import { Order } from './order';
import { Customer } from '../customer/customer';
import { Creditor } from '../creditor/creditor';
import { AuthService } from '../auth.service';

@Component({
  selector: 'order-detail',
  templateUrl: './order-detail.component.html',
  styleUrls: ['./order-detail.component.css'],
  providers: [OrderService]
})
export class OrderDetailComponent implements OnInit {

  customer: Customer;
  creditor: Creditor;
  orders: Order[];
  page: number;
  limit: number;

  totalPages: number[];

  constructor(private orderService: OrderService, private authService: AuthService, private router: Router) {
    this.page = 1;
    this.limit = 3;
  }

  getOrders() {
    this.orderService.getOrders(this.page, this.limit).subscribe(res => {
      //this.orders = res;
      console.log(res);
    });
  }

  ngOnInit(): void {
    this.orderService.getOrders(this.page, this.limit).subscribe(res => {
      this.orders = res.content;
      this.totalPages = Array(res.totalPages).fill(0).map((i, x)=> x + 1);
      console.log(res);
    }, err => {
      this.router.navigate(["/login"]);
    });
  }

  setPage(page: number) {
    this.page = page;
    this.orderService.getOrders(page, this.limit).subscribe(res => {
      this.orders = res.content;
    })
  }

}
