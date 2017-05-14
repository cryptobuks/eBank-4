import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Order } from './order';

@Injectable()
export class OrderService {

  private orderUrl = '/api/orders';

  constructor(private http: Http) {

  }

  getOrders(): Observable<Order[]> {
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
    return this.http.get(this.orderUrl, {headers}).map(res => res.json());
  }

}
