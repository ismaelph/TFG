import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SolicitudPersonalizada } from 'src/app/core/interfaces/SolicitudPersonalizada';
import { NuevaSolicitudPersonalizada } from 'src/app/core/interfaces/NuevaSolicitudPersonalizada';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { PERSONALIZADA_ENDPOINT } from 'src/app/core/constants/constants';

@Injectable({
  providedIn: 'root'
})
export class SolicitudPersonalizadaService {
  private baseUrl = PERSONALIZADA_ENDPOINT;

  constructor(private http: HttpClient) {}

  crearSolicitud(dto: NuevaSolicitudPersonalizada): Observable<any> {
    return this.http.post(`${this.baseUrl}/crear`, dto).pipe(
      tap(() => console.log('📨 Solicitud personalizada creada correctamente.')),
      catchError(err => {
        console.error('❌ Error al crear solicitud personalizada:', err);
        return of(null);
      })
    );
  }

  obtenerMisSolicitudes(usuarioId: number): Observable<SolicitudPersonalizada[]> {
    return this.http.get<SolicitudPersonalizada[]>(`${this.baseUrl}/usuario/${usuarioId}`).pipe(
      tap(data => console.log(`📂 Solicitudes personalizadas del usuario ${usuarioId}:`, data)),
      catchError(err => {
        console.error(`❌ Error al obtener solicitudes personalizadas del usuario ${usuarioId}:`, err);
        return of([]);
      })
    );
  }

  obtenerPorEmpresa(): Observable<SolicitudPersonalizada[]> {
    return this.http.get<SolicitudPersonalizada[]>(`${this.baseUrl}/empresa`).pipe(
      tap(data => console.log('🏢 Solicitudes personalizadas por empresa:', data)),
      catchError(err => {
        console.error('❌ Error al obtener solicitudes por empresa:', err);
        return of([]);
      })
    );
  }

  actualizarEstado(id: number, solicitud: Partial<SolicitudPersonalizada>): Observable<SolicitudPersonalizada> {
    return this.http.put<SolicitudPersonalizada>(`${this.baseUrl}/${id}/estado`, solicitud).pipe(
      tap(data => console.log(`🔄 Estado actualizado para solicitud personalizada ${id}:`, data)),
      catchError(err => {
        console.error(`❌ Error al actualizar estado de solicitud personalizada ${id}:`, err);
        return of({ ...solicitud, id } as SolicitudPersonalizada);
      })
    );
  }

  marcarLeida(id: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}/marcar-leida`, {}).pipe(
      tap(() => console.log(`✅ Solicitud personalizada ${id} marcada como leída.`)),
      catchError(err => {
        console.error(`❌ Error al marcar como leída la solicitud personalizada ${id}:`, err);
        return of(null);
      })
    );
  }

  eliminar(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`).pipe(
      tap(() => console.log(`🗑 Solicitud personalizada eliminada ID ${id}`)),
      catchError(err => {
        console.error(`❌ Error al eliminar solicitud personalizada ID ${id}:`, err);
        return of(null);
      })
    );
  }

  getNoLeidas(): Observable<SolicitudPersonalizada[]> {
    return this.http.get<SolicitudPersonalizada[]>(`${this.baseUrl}/noleidas`).pipe(
      tap(data => console.log('🔔 Solicitudes personalizadas NO leídas:', data)),
      catchError(err => {
        console.error('❌ Error al obtener solicitudes personalizadas no leídas:', err);
        return of([]);
      })
    );
  }
}
