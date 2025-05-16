import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from 'src/app/core/interfaces/user.interface';
import { Empresa } from 'src/app/core/interfaces/empresa';
import { RegistroMensual } from 'src/app/core/interfaces/registro-mensual.interface';
import { ErrorSistema } from 'src/app/core/interfaces/error-sistema.interface';
import { EstadoSistema } from 'src/app/core/interfaces/estado-sistema.interface';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = `${environment.apiUrl}/api/admin`;

  constructor(private http: HttpClient) {}

  getUsuarios(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrl}/usuarios`);
  }

  getEmpresas(): Observable<Empresa[]> {
    return this.http.get<Empresa[]>(`${this.apiUrl}/empresas`);
  }

  getUsuariosPorMes(): Observable<RegistroMensual[]> {
    return this.http.get<RegistroMensual[]>(`${this.apiUrl}/stats/usuarios-por-mes`);
  }

  getErroresSistema(): Observable<ErrorSistema[]> {
    return this.http.get<ErrorSistema[]>(`${this.apiUrl}/errores`);
  }

  getEstadoSistema(): Observable<EstadoSistema> {
    return this.http.get<EstadoSistema>(`${this.apiUrl}/estado`);
  }

  enviarCorreoGlobal(asunto: string, mensaje: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/enviar-correo`, { asunto, mensaje });
  }
}
