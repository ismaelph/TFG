import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SolicitudMovimiento } from 'src/app/core/interfaces/SolicitudMovimiento';
import { NuevaSolicitudMovimiento } from 'src/app/core/interfaces/NuevaSolicitudMovimiento';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { SOLICITUD_ENDPOINT } from 'src/app/core/constants/constants';

@Injectable({
  providedIn: 'root'
})
export class SolicitudMovimientoEmpleadoService {

  private baseUrl = SOLICITUD_ENDPOINT;

  constructor(private http: HttpClient) { }

  getTodas(): Observable<SolicitudMovimiento[]> {
    return this.http.get<SolicitudMovimiento[]>(this.baseUrl).pipe(
      tap(data => console.log('📂 Todas las solicitudes (admin):', data)),
      catchError(err => {
        console.error('❌ Error al obtener todas las solicitudes:', err);
        return of([]);
      })
    );
  }

  getPorUsuario(id: number): Observable<SolicitudMovimiento[]> {
    return this.http.get<SolicitudMovimiento[]>(`${this.baseUrl}/usuario/${id}`).pipe(
      tap(data => console.log(`👤 Solicitudes del usuario ${id}:`, data)),
      catchError(err => {
        console.error(`❌ Error al obtener solicitudes del usuario ${id}:`, err);
        return of([]);
      })
    );
  }

  getPorEmpresa(): Observable<SolicitudMovimiento[]> {
    return this.http.get<SolicitudMovimiento[]>(`${this.baseUrl}/empresa`).pipe(
      tap(data => console.log('🏢 Solicitudes de mi empresa:', data)),
      catchError(err => {
        console.error('❌ Error al obtener solicitudes por empresa:', err);
        return of([]);
      })
    );
  }

  getNoLeidas(): Observable<SolicitudMovimiento[]> {
    return this.http.get<SolicitudMovimiento[]>(`${this.baseUrl}/noleidas`).pipe(
      tap(data => console.log('🔔 Solicitudes NO leídas:', data)),
      catchError(err => {
        console.error('❌ Error al obtener solicitudes no leídas:', err);
        return of([]);
      })
    );
  }

  crear(dto: NuevaSolicitudMovimiento): Observable<any> {
    return this.http.post(`${this.baseUrl}/crear`, dto).pipe(
      tap(() => console.log('📨 Solicitud de movimiento creada correctamente.')),
      catchError(err => {
        console.error('❌ Error al crear solicitud de movimiento:', err);
        return of(null);
      })
    );
  }

  actualizarEstado(id: number, solicitud: Partial<SolicitudMovimiento>): Observable<SolicitudMovimiento> {
    return this.http.put<SolicitudMovimiento>(`${this.baseUrl}/${id}/estado`, solicitud).pipe(
      tap(data => console.log(`🔄 Estado actualizado para solicitud ${id}:`, data)),
      catchError(err => {
        console.error(`❌ Error al actualizar estado de solicitud ${id}:`, err);
        return of({ ...solicitud, id } as SolicitudMovimiento);
      })
    );
  }

  marcarLeida(id: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}/marcar-leida`, {}).pipe(
      tap(() => console.log(`✅ Solicitud ${id} marcada como leída.`)),
      catchError(err => {
        console.error(`❌ Error al marcar como leída la solicitud ${id}:`, err);
        return of(null);
      })
    );
  }

  eliminar(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`).pipe(
      tap(() => console.log(`🗑 Solicitud eliminada ID ${id}`)),
      catchError(err => {
        console.error(`❌ Error al eliminar solicitud ID ${id}:`, err);
        return of(null);
      })
    );
  }

  getNoLeidasEmpleado(userId: number): Observable<SolicitudMovimiento[]> {
    return this.http.get<SolicitudMovimiento[]>(`${this.baseUrl}/usuario/${userId}/noleidas`).pipe(
      tap(data => console.log(`🔔 Solicitudes NO leídas del empleado ID ${userId}:`, data)),
      catchError(err => {
        console.error('❌ Error al obtener solicitudes no leídas del empleado:', err);
        return of([]);
      })
    );
  }

  marcarLeidaEmpleado(id: number): Observable<any> {
  return this.http.put(`${this.baseUrl}/${id}/leida-empleado`, {}).pipe(
    tap(() => console.log(`✅ Solicitud ${id} marcada como leída por el empleado.`)),
    catchError(err => {
      console.error(`❌ Error al marcar como leída por el empleado la solicitud ${id}:`, err);
      return of(null);
    })
  );
}


}
