import { Component, OnInit } from '@angular/core';


import { Order } from '../orders/order';
import { OrderService } from '../orders/order.service';

@Component({
  selector: 'admin-orders',
  templateUrl: './admin-orders.component.html',
  providers: [OrderService]
})
export class AdminOrdersComponent implements OnInit {

  orders: Order[];

  constructor(private orderService: OrderService) {

  }

  ngOnInit() {
    this.orderService.getAllOrders().subscribe(res => {
      this.orders = res;
    });
  }

  editOrder(order: Order) {
    console.log(order);
  }

}
