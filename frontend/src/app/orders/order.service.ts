import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Order } from './order';

@Injectable()
export class OrderService {

  private orderUrl = '/api/orders/user';
  private createOrderUrl = "/api/order/create";
  private editOrderUrl = '/api/admin/order/edit';
  private adminOrdersUrl = '/api/admin/orders'

  // TODO:
  // get orders by id

  constructor(private http: Http) {

  }

  getOrders(page: number, limit: number) {
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
    let params = new URLSearchParams();
    params.append("p", page.toString());
    params.append("l", limit.toString());

    return this.http.get(this.orderUrl, { headers, params}).map(res => res.json());
  }

  makeOrder(order: Order): Observable<Order> {
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
    return this.http.post(this.createOrderUrl, order, {headers}).map(res => res.json());
  }

  getAllOrders(page: number, limit: number) {
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
    let params = new URLSearchParams();
    params.append("p", page.toString());
    params.append("l", limit.toString());
    return this.http.get(this.adminOrdersUrl, {headers, params}).map(res => res.json());
  }

  editOrder(order: Order): Observable<Order> {
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
      return this.http.put(this.editOrderUrl, order, {headers}).map(res => res.json());
  }


}
