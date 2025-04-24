import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment'; // Importa environment

@Injectable({
  providedIn: 'root'
})
export class RestablecerContraseñaService {

  private URL_CORREO = `${environment.urlBackendSpring}`; // URL base del backend

  constructor(private httpClient: HttpClient) { }

  // Método para solicitar el restablecimiento de contraseña
  resetPasswordRequest(email: string): Observable<any> {
    return this.httpClient.post<any>(`${this.URL_CORREO}/reset-password-request`, { email });
  }

  // Validar token
  validarToken(email: string, token: string): Observable<any> {
    return this.httpClient.get(`${this.URL_CORREO}/validar-token`, { 
      params: { email, token }
    });
  }
  
  // Restablecer la contraseña
  resetPassword(email: string, token: string, password: string, password_confirmation: string): Observable<any> {
    return this.httpClient.post(`${this.URL_CORREO}/reset-password`, {
      email,
      token,
      password,
      password_confirmation
    });
  }
}