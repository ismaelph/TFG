import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from 'src/app/core/interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = `${environment.apiUrl}/api/usuarios`;

  constructor(private http: HttpClient) {}

  getUsuariosDeMiEmpresa(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/mi-empresa`);
  }

  expulsarDeEmpresa(id: number): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}/expulsar`, {});
  }
}
