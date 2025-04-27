import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AutenticacionService {
  private baseUrl = `${environment.urlBackendSpring}/api/auth`;
  private URL_LOGIN = `${this.baseUrl}/login`;
  private sesionIniciadaSubject = new BehaviorSubject<boolean>(this.isTokenPresent());

  constructor(private httpClient: HttpClient) {}

  iniciarSesion(login: string, pass: string): Observable<boolean> {
    const credenciales = { username: login, password: pass };

    return this.httpClient.post<{ token: string }>(this.URL_LOGIN, credenciales).pipe(
      map((response) => {
        // Guardar el token en LocalStorage
        localStorage.setItem('jwtToken', response.token);
        this.sesionIniciadaSubject.next(true); // Notificar que la sesión ha iniciado
        return true;
      }),
      catchError((error) => {
        console.error('Error al iniciar sesión:', error);
        return throwError(() => error);
      })
    );
  }

  registrarUsuario(credenciales: { username: string; email: string; password: string }): Observable<any> {
    const URL_SIGNUP = `${this.baseUrl}/signup`;
    return this.httpClient.post(URL_SIGNUP, credenciales);
  }

  cerrarSesion(): void {
    // Eliminar el token del LocalStorage
    localStorage.removeItem('jwtToken');
    this.sesionIniciadaSubject.next(false); // Notificar que la sesión ha terminado
  }

  isSesionIniciada(): Observable<boolean> {
    return this.sesionIniciadaSubject.asObservable();
  }

  private isTokenPresent(): boolean {
    return !!localStorage.getItem('jwtToken'); // Verifica si el token existe en LocalStorage
  }

  getToken(): string | null {
    return localStorage.getItem('jwtToken'); // Recupera el token del LocalStorage
  }

  // PERFIL
  // Métodos para obtener y actualizar el perfil del usuario

  obtenerPerfil(): Observable<any> {
    return this.httpClient.get(`${this.baseUrl}/profile`);
  }

  actualizarPerfil(datos: any): Observable<any> {
    return this.httpClient.put(`${this.baseUrl}/profile`, datos);
  }
}