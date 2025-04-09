import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { LoginRequest, LoginResponse, RegisterRequest, RegisterResponse } from '../interfaces/autentication';

@Injectable({
  providedIn: 'root'
})
export class AutenticationService {
  private URL_LOGIN = `${environment.urlBackendSpring}/auth/login`;
  private URL_REGISTER = `${environment.urlBackendSpring}/auth/register`;
  private jwtToken: string | null = null;

  constructor(private httpClient: HttpClient) {}

  /**
   * Inicia sesión en el sistema.
   * @param login Credenciales del usuario.
   * @returns Observable<boolean>
   */
  iniciarSesion(login: string, pass: string): Observable<boolean> {
    const credenciales: LoginRequest = { username: login, password: pass };

    return this.httpClient.post<LoginResponse>(this.URL_LOGIN, credenciales).pipe(
      map((response) => {
        this.jwtToken = response.token;
        localStorage.setItem('jwtToken', this.jwtToken); // Guarda el token en localStorage
        localStorage.setItem('user', JSON.stringify(response.user)); // Guarda los datos del usuario
        return true;
      })
    );
  }


  /**
   * Cierra la sesión del usuario.
   */
  cerrarSesion(): void {
    this.jwtToken = null;
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('user');
  }

  /**
   * Verifica si la sesión está iniciada.
   * @returns boolean
   */
  isSesionIniciada(): boolean {
    return !!localStorage.getItem('jwtToken');
  }

  /**
   * Obtiene el token JWT almacenado.
   * @returns string | null
   */
  getJwtToken(): string | null {
    return localStorage.getItem('jwtToken');
  }

  /**
   * Obtiene los datos del usuario autenticado.
   * @returns any
   */
  getUsuario(): any {
    const user = localStorage.getItem('user');
    return user ? JSON.parse(user) : null;
  }
  
  /**
  * Registra un nuevo usuario.
  * @param registro Datos del usuario a registrar.
  * @returns Observable<RegisterResponse>
  */
  registrarUsuario(registro: RegisterRequest): Observable<RegisterResponse> {
    return this.httpClient.post<RegisterResponse>(this.URL_REGISTER, registro);
  }
  
}  