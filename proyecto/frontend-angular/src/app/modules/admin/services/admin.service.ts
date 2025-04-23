import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../../../interfaces/user';
import { Company } from '../../../interfaces/company';
import { environment } from 'src/environments/environment'; 

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrlempresas = `${environment.urlBackendSpring}/empresas`; 
  private apiUrlusuarios = `${environment.urlBackendSpring}/api/auth`; 

  constructor(private http: HttpClient) {}

  // Métodos para gestionar usuarios
  obtenerUsuarios(): Observable<User[]> {
    return this.http.get<User[]>(`${this.apiUrlusuarios}`);
  }

  obtenerUsuarioPorId(idUsuario: number): Observable<User> {
    return this.http.get<User>(`${this.apiUrlusuarios}/${idUsuario}`);
  }

  crearUsuario(datosUsuario: Partial<User>): Observable<User> {
    return this.http.post<User>(`${this.apiUrlusuarios}/signup`, datosUsuario);
  }

  actualizarUsuario(idUsuario: number, datosUsuario: Partial<User>): Observable<User> {
    return this.http.put<User>(`${this.apiUrlusuarios}/${idUsuario}`, datosUsuario);
  }

  eliminarUsuario(idUsuario: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrlusuarios}/${idUsuario}`);
  }

  // Métodos para gestionar empresas
  obtenerEmpresas(): Observable<Company[]> {
    return this.http.get<Company[]>(`${this.apiUrlempresas}`);
  }

  obtenerEmpresaPorNombre(nombre: string): Observable<Company> {
    return this.http.get<Company>(`${this.apiUrlempresas}/${nombre}`);
  }

  crearEmpresa(datosEmpresa: Partial<Company>): Observable<Company> {
    return this.http.post<Company>(`${this.apiUrlempresas}`, datosEmpresa);
  }

  actualizarEmpresa(idEmpresa: number, datosEmpresa: Partial<Company>): Observable<Company> {
    return this.http.put<Company>(`${this.apiUrlempresas}/${idEmpresa}`, datosEmpresa);
  }

  eliminarEmpresa(idEmpresa: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrlempresas}/${idEmpresa}`);
  }
}