import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Planta } from 'src/app/core/interfaces/planta';

@Injectable({
  providedIn: 'root'
})
export class PlantaService {
  private apiUrl = `${environment.apiUrl}/api/plantas`;

  constructor(private http: HttpClient) {}

  // POST – Crear nueva planta
  crear(planta: Planta): Observable<Planta> {
    return this.http.post<Planta>(this.apiUrl, planta);
  }

  // GET – Obtener todas las plantas
  obtenerTodas(): Observable<Planta[]> {
    return this.http.get<Planta[]>(this.apiUrl);
  }

  // GET – Obtener plantas por ID de almacén
  obtenerPorAlmacen(almacenId: number): Observable<Planta[]> {
    return this.http.get<Planta[]>(`${this.apiUrl}/almacen/${almacenId}`);
  }

  // PUT – Editar planta
  actualizar(id: number, planta: Planta): Observable<Planta> {
    return this.http.put<Planta>(`${this.apiUrl}/${id}`, planta);
  }

  // DELETE – Eliminar planta
  eliminar(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  // GET – Obtener planta por ID
  obtenerPorId(id: number): Observable<Planta> {
    return this.http.get<Planta>(`${this.apiUrl}/${id}`);
  }
}
