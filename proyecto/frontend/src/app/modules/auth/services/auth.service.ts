import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LOGIN_ENDPOINT, REGISTER_ENDPOINT } from 'src/app/core/constants/constants';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {}

  login(credentials: any): Observable<any> {
    return this.http.post(`${LOGIN_ENDPOINT}`, credentials);
  }

  signup(data: any): Observable<any> {
    return this.http.post(`${REGISTER_ENDPOINT}`, data);
  }
}
