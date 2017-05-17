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
  selectedOrder: Order;

  constructor(private orderService: OrderService) {

  }

  ngOnInit() {
    this.orderService.getAllOrders().subscribe(res => {
      this.orders = res;
    });
  }

  setEditableOrder(order: Order) {
    this.selectedOrder = order;
  }

  editOrder(order: Order) {
    this.orderService.editOrder(order).subscribe(res => {
      console.log(res);
    })
  }

}
