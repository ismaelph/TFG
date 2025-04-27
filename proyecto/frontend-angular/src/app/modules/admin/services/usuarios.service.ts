import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Usuario } from 'src/app/interfaces/usuario';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuariosService {

  private apiUrl = `${environment.urlBackendSpring}/api/auth`;

  constructor(private http: HttpClient) {}

  get(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(`${this.apiUrl}`);
  }

  getId(idUsuario: number): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.apiUrl}/${idUsuario}`);
  }

  post(datosUsuario: Partial<Usuario>): Observable<Usuario> {
    return this.http.post<Usuario>(`${this.apiUrl}/signup`, datosUsuario);
  }

  put(idUsuario: number, datosUsuario: Partial<Usuario>): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.apiUrl}/${idUsuario}`, datosUsuario);
  }

  delete(idUsuario: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${idUsuario}`);
  }
}
