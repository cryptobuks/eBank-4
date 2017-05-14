import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthService {

  private authUrl = "/api/user";

  constructor(private http: Http) {

  }

  logIn(username: string, password: string) {
    let headers = new Headers();
    headers.append('Authorization', 'Basic ' + btoa(username + ':' + password));
    headers.append('X-Requested-With', 'XMLHttpRequest');
    return this.http.get(this.authUrl, {headers}).map(res => res.json());
  }

  private getUser() {
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
    return this.http.get(this.authUrl, {headers}).map(res => res.json());
  }

  isAuthenticated() {
    return this.getUser();
  }

}
