import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Order } from './order';

@Injectable()
export class OrderService {

  private orderUrl = '/api/orders/user';
  private createOrderUrl = "/api/order/create";

  // TODO:
  // admin order edit
  private editOrderUrl = '/api/admin/order/edit';

  // TODO:
  // admin list all orders
  private adminOrdersUrl = '/api/admin/orders'

  // TODO:
  // get orders by id

  constructor(private http: Http) {

  }

  getOrders(): Observable<Order[]> {
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
    return this.http.get(this.orderUrl, {headers}).map(res => res.json());
  }

  makeOrder(order: Order): Observable<Order> {
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
    return this.http.post(this.createOrderUrl, order, {headers}).map(res => res.json());
  }

  getAllOrders(): Observable<Order[]> {
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
    return this.http.get(this.adminOrdersUrl, {headers}).map(res => res.json());
  }


}
