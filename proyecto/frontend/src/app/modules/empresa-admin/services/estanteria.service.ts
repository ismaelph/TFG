import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Estanteria } from 'src/app/core/interfaces/estanteria';

@Injectable({
  providedIn: 'root'
})
export class EstanteriaService {
  private apiUrl = `${environment.apiUrl}/api/estanterias`;

  constructor(private http: HttpClient) {}

  // POST – Crear estantería
  crear(estanteria: Estanteria): Observable<Estanteria> {
    return this.http.post<Estanteria>(this.apiUrl, estanteria);
  }

  // GET – Obtener estanterías por planta
  obtenerPorPlanta(plantaId: number): Observable<Estanteria[]> {
    return this.http.get<Estanteria[]>(`${this.apiUrl}/planta/${plantaId}`);
  }

  // GET – Obtener por ID
  obtenerPorId(id: number): Observable<Estanteria> {
    return this.http.get<Estanteria>(`${this.apiUrl}/${id}`);
  }

  // PUT – Actualizar estantería
  actualizar(id: number, estanteria: Estanteria): Observable<Estanteria> {
    return this.http.put<Estanteria>(`${this.apiUrl}/${id}`, estanteria);
  }

  // DELETE – Eliminar estantería
  eliminar(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
