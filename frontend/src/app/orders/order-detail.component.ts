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

  constructor(private orderService: OrderService, private authService: AuthService, private router: Router) {

  }

  getOrders() {
    this.orderService.getOrders().subscribe(res => {
      this.orders = res;
    });
  }

  ngOnInit(): void {
    this.orderService.getOrders().subscribe(res => {
      this.orders = res;
    }, err => {
      this.router.navigate(["/login"]);
    });

  }

}
