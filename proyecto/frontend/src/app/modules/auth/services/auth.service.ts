import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LOGIN_ENDPOINT, REGISTER_ENDPOINT } from 'src/app/core/constants/constants';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  login(credentials: any): Observable<any> {
    return this.http.post(`${LOGIN_ENDPOINT}`, credentials);
  }

  signup(data: any): Observable<any> {
    return this.http.post(`${REGISTER_ENDPOINT}`, data);
  }

  getPerfil(id: number): Observable<any> {
    return this.http.get(`${environment.apiUrl}/api/usuarios/${id}`);
  }

  actualizarPerfil(id: number, data: any): Observable<any> {
    return this.http.put(`${environment.apiUrl}/api/usuarios/${id}`, data);
  }

}
