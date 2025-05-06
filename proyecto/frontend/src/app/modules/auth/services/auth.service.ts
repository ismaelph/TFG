import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { LOGIN_ENDPOINT, REGISTER_ENDPOINT } from 'src/app/core/constants/constants';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private API = environment.apiUrl;

  constructor(private http: HttpClient) {}

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.API}${LOGIN_ENDPOINT}`, credentials);
  }

  signup(data: any): Observable<any> {
    return this.http.post(`${this.API}${REGISTER_ENDPOINT}`, data);
  }
}
