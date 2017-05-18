import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { Order } from './order';
import { OrderService } from './order.service';
import { AuthService } from '../auth.service';

@Component({
  selector: 'order-make',
  templateUrl: './order-make.component.html',
  styleUrls: ['./order-make.component.css'],
  providers: [OrderService, AuthService]
})
export class OrderMakeComponent {

  order: Order;
  error: string;

  constructor(private orderService: OrderService, private authService: AuthService, private router: Router) {
    this.order = new Order();
    // this.orderService.getOrders().subscribe(res => {
    //   this.order = res[0];
    // });
  }

  try() {
    this.orderService.makeOrder(this.order).subscribe(res => {
      console.log(this.order);
      this.router.navigate(["/order/history"]);
    }, err => {
      this.error = err.json().message;
      console.log(err.json());
    });
  }

  ngOnInit() {
    this.authService.isAuthenticated().subscribe(res => {

    }, err => {
      this.router.navigate(["/login"]);
    });
  }

}
