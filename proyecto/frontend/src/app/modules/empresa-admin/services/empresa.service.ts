import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Empresa } from '../../../core/interfaces/empresa';
import { EMPRESA_ENDPOINT } from 'src/app/core/constants/constants';

@Injectable({
  providedIn: 'root'
})
export class EmpresaService {
  private baseUrl = EMPRESA_ENDPOINT;

  constructor(private http: HttpClient) { }

  // Obtener empresa del usuario autenticado
  getMiEmpresa(): Observable<Empresa> {
    return this.http.get<Empresa>(`${this.baseUrl}/mi-empresa`);
  }

  // Obtener empresa por ID
  getEmpresaById(id: number): Observable<Empresa> {
    return this.http.get<Empresa>(`${this.baseUrl}/${id}`);
  }

  // Crear empresa
  crearEmpresa(empresa: Empresa): Observable<Empresa> {
    return this.http.post<Empresa>(this.baseUrl, empresa);
  }

  // Actualizar empresa
  actualizarEmpresa(id: number, empresa: Empresa): Observable<Empresa> {
    return this.http.put<Empresa>(`${this.baseUrl}/${id}`, empresa);
  }

  // Eliminar empresa
  eliminarEmpresa(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }

  // Cambiar clave de empresa
  cambiarClave(id: number, nuevaClave: string): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}/cambiar-clave`, { nuevaClave });
  }

  // Enviar correo a empleados (restaurado)
  enviarCorreoEmpleados(asunto: string, mensaje: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/empresa/enviar-correo`, {
      asunto,
      mensaje
    });
  }
}
