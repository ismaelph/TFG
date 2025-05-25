import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, tap } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LOGIN_ENDPOINT, REGISTER_ENDPOINT } from 'src/app/core/constants/constants';
import {
  LoginRequest,
  SignupRequest,
  JwtResponse,
  ForgotPasswordRequest,
  ResetPasswordRequest,
  CambiarPasswordDto
} from '../interfaces/auth';
import { User } from 'src/app/core/interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  // Login
  login(credentials: LoginRequest): Observable<JwtResponse> {
    return this.http.post<JwtResponse>(`${LOGIN_ENDPOINT}`, credentials);
  }

  // Registro
  signup(data: SignupRequest): Observable<any> {
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

  // Enviar enlace de recuperación
  forgotPassword(data: ForgotPasswordRequest): Observable<any> {
    console.log('📤 Enviando forgotPassword payload:', data);

    return this.http.post(`${environment.apiUrl}/api/auth/forgot-password`, data, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    }).pipe(
      tap((res) => console.log('✅ Respuesta forgotPassword:', res)),
      catchError((error) => {
        console.error('❌ Error en forgotPassword:', error);
        throw error;
      })
    );
  }

  // Restablecer contraseña con token
  resetPassword(data: ResetPasswordRequest): Observable<any> {
    console.log('📤 Enviando resetPassword payload:', data);

    return this.http.post(`${environment.apiUrl}/api/auth/reset-password`, data, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    }).pipe(
      tap((res) => console.log('✅ Respuesta resetPassword:', res)),
      catchError((error) => {
        console.error('❌ Error en resetPassword:', error);
        throw error;
      })
    );
  }

  cambiarPassword(id: number, data: CambiarPasswordDto): Observable<any> {
    console.log('📤 Enviando cambio de contraseña para userID:', id, 'Payload:', data);

    return this.http.put(`${environment.apiUrl}/api/usuarios/${id}/cambiar-password`, data, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    }).pipe(
      tap(() => console.log('✅ Contraseña cambiada en backend')),
      catchError((error) => {
        console.error('❌ Error en cambiarPassword:', error);
        throw error;
      })
    );
  }


}
