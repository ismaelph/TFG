import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClientService {
  private baseUrl = `${environment.urlBackendSpring}/api/user`;

  constructor(private httpClient: HttpClient) {}

  // PERFIL
  // Métodos para obtener y actualizar el perfil del usuario

  obtenerPerfil(): Observable<any> {
    const token = this.getToken(); // Recuperar el token del LocalStorage
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get(`${this.baseUrl}/profile`, { headers });
  }
  
  actualizarPerfil(datos: any): Observable<any> {
    const token = this.getToken(); // Recuperar el token del LocalStorage
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.put(`${this.baseUrl}/profile`, datos, { headers });
  }

  // Método para obtener el token del LocalStorage
  private getToken(): string | null {
    return localStorage.getItem('jwtToken'); // Recupera el token del LocalStorage
  }
}