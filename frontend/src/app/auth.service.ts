import { Injectable } from '@angular/core';
import { Http, Headers } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthService {

  redirectUrl = "/login";
  private authUrl = "/api/user";
  isLoggedIn: boolean = false;
  role: string;

  constructor(private http: Http) {

  }

  logIn(username: string, password: string){
    let headers = new Headers();
    headers.append('Authorization', 'Basic ' + btoa(username + ':' + password));
    headers.append('X-Requested-With', 'XMLHttpRequest');
    return this.http.get(this.authUrl, {headers}).map(res => {
      let r = res.json();
      this.isLoggedIn = true;
      this.role = r.authorities[0].authority;
    }, err => {
			console.log(err);
		});
  }

  logout() {
    this.isLoggedIn = false;
    return this.http.post("/api/logout", {});
  }

  private getUser() {
    let headers = new Headers();
    headers.append('X-Requested-With', 'XMLHttpRequest');
    return this.http.get(this.authUrl, {headers});
  }

  isAuthenticated() {
    return this.getUser();
  }

}
