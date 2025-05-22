import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { Almacen } from 'src/app/core/interfaces/almacen';

@Injectable({
  providedIn: 'root'
})
export class AlmacenService {
  private apiUrl = `${environment.apiUrl}/api/almacenes`;

  constructor(private http: HttpClient) { }

  // POST – Crear nuevo almacén
  crear(almacen: Almacen): Observable<Almacen> {
    return this.http.post<Almacen>(this.apiUrl, almacen);
  }

  // GET – Obtener todos los almacenes
  obtenerTodos(): Observable<Almacen[]> {
    return this.http.get<Almacen[]>(this.apiUrl);
  }

  // GET – Obtener almacén por ID
  obtenerPorId(id: number): Observable<Almacen> {
    return this.http.get<Almacen>(`${this.apiUrl}/${id}`);
  }

  // PUT – Editar almacén
  actualizar(id: number, almacen: Almacen): Observable<Almacen> {
    return this.http.put<Almacen>(`${this.apiUrl}/${id}`, almacen);
  }

  // DELETE – Eliminar almacén
  eliminar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }


  // GET – Obtener almacenes por empresa
  obtenerPorEmpresa(empresaId: number): Observable<Almacen[]> {
    return this.http.get<Almacen[]>(`${this.apiUrl}/empresa/${empresaId}`);
  }
}
