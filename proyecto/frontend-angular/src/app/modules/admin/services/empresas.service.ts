import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Empresa } from 'src/app/interfaces/empresa';

@Injectable({
  providedIn: 'root'
})
export class EmpresasService {
  private apiUrlempresas = `${environment.urlBackendSpring}/empresas`; 
  
  constructor(private http: HttpClient) {}

  get(): Observable<Empresa[]> {
    return this.http.get<Empresa[]>(`${this.apiUrlempresas}`);
  }

  getNombre(nombre: string): Observable<Empresa> {
    return this.http.get<Empresa>(`${this.apiUrlempresas}/${nombre}`);
  }

  post(datosEmpresa: Partial<Empresa>): Observable<Empresa> {
    return this.http.post<Empresa>(`${this.apiUrlempresas}`, datosEmpresa);
  }

  put(idEmpresa: number, datosEmpresa: Partial<Empresa>): Observable<Empresa> {
    return this.http.put<Empresa>(`${this.apiUrlempresas}/${idEmpresa}`, datosEmpresa);
  }

  delete(idEmpresa: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrlempresas}/${idEmpresa}`);
  }
}
