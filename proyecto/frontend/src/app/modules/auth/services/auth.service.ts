import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LOGIN_ENDPOINT, REGISTER_ENDPOINT } from 'src/app/core/constants/constants';
import { User } from 'src/app/core/interfaces/user.interface';
import { JwtResponse } from 'src/app/core/interfaces/JwtResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  // Login
  login(credentials: any): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${LOGIN_ENDPOINT}`, credentials);
  }

  // Registro
  signup(data: any): Observable<any> {
    return this.http.post(`${REGISTER_ENDPOINT}`, data);
  }

  // Obtener perfil del usuario
  getPerfil(id: number): Observable<User> {
    return this.http.get<User>(`${environment.apiUrl}/api/usuarios/${id}`);
  }

  // Actualizar perfil (retorna nuevo token)
  actualizarPerfil(id: number, data: User): Observable<JwtResponse> {
    return this.http.put<JwtResponse>(`${environment.apiUrl}/api/usuarios/${id}`, data);
  }
}
