import { Injectable } from '@angular/core';
import { Http, Headers, Response, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { Customer } from './customer';

@Injectable()
export class CustomerService {

  private customersUrl = '/api/admin/customers';

  private customerUrl = '/api/customer';
  private registerCustomerUrl = "/api/customer/register";
  private customerEditUrl = '/api/admin/customer/edit';

  constructor(private http: Http) {

  }

  getCustomer(): Observable<Customer> {
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
    return this.http.get(this.customerUrl, {headers}).map(res => res.json());
  }

  registerCustomer(customer: Customer): Observable<Customer> {
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
    return this.http.post(this.registerCustomerUrl, customer, {headers}).map(res => res.json());
  }

  getCustomers(page: number, limit: number) {
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
    let params = new URLSearchParams();
    params.append("p", page.toString());
    params.append("l", limit.toString());
    return this.http.get(this.customersUrl, {headers, params}).map(res => res.json());
  }

  editCustomer(customer: Customer): Observable<Customer> {
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
    return this.http.put(this.customerEditUrl, customer, {headers}).map(res => res.json());
  }

}
