import { Component } from '@angular/core';

import { Order } from './order';
import { OrderService } from './order.service';

@Component({
  selector: 'order-make',
  templateUrl: './order-make.component.html',
  styleUrls: ['./order-make.component.css'],
  providers: [OrderService]
})
export class OrderMakeComponent {

  order: Order;

  constructor(private orderService: OrderService) {
    this.order = new Order();
    // this.orderService.getOrders().subscribe(res => {
    //   this.order = res[0];
    // });
  }

  try() {
    console.log(this.order);
  }

}
