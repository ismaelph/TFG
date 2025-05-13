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

  constructor(private http: HttpClient) {}

  
  getMiEmpresa(): Observable<Empresa> {
    return this.http.get<Empresa>(`${this.baseUrl}/mi-empresa`);
  }

  // ➕ Crear nueva empresa
  crearEmpresa(empresa: Empresa): Observable<Empresa> {
    return this.http.post<Empresa>(this.baseUrl, empresa);
  }

  // 🔍 Obtener empresa por ID (solo si tienes acceso)
  getEmpresaById(id: number): Observable<Empresa> {
    return this.http.get<Empresa>(`${this.baseUrl}/${id}`);
  }

  // ✏️ Actualizar empresa
  actualizarEmpresa(id: number, empresa: Empresa): Observable<Empresa> {
    return this.http.put<Empresa>(`${this.baseUrl}/${id}`, empresa);
  }

  // ❌ Eliminar empresa
  eliminarEmpresa(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
