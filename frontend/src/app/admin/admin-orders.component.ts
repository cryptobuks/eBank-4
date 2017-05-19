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

  limit: number;
  page: number;

  totalPages: number[];

  constructor(private orderService: OrderService) {
    this.limit = 3;
    this.page = 1;
  }

  ngOnInit() {
    this.orderService.getAllOrders(this.page, this.limit).subscribe(res => {
      this.orders = res.content;
      this.totalPages = Array(res.totalPages).fill(0).map((x, i) => i+1);
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

  setPage(page: number) {
    this.page = page;
    this.orderService.getAllOrders(page, this.limit).subscribe(res => {
      this.orders = res.content;
    })
  }

}
