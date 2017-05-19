import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class CurrencyService {

  currenciesUrl = '/api/currencies';

  constructor(private http: Http) {

  }

  getCurrencies() {
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
    return this.http.get(this.currenciesUrl, {headers}).map(res => res.json());
  }

  getValue(from: string, to: string, value: string){
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
    return this.http.get('/api/currency/' + from + '/' + to + '/' + value, {headers})
                    .map(res => res.json());
  }

}
