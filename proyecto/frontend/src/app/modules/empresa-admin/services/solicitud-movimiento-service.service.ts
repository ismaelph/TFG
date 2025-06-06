import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SolicitudMovimiento } from 'src/app/core/interfaces/SolicitudMovimiento';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SolicitudMovimientoService {
  private baseUrl = `${environment.apiUrl}/api/solicitudes/movimiento`;

  constructor(private http: HttpClient) {}

  // 🔄 Obtener todas las solicitudes normales no leídas del admin actual
  getSolicitudesNoLeidas(): Observable<SolicitudMovimiento[]> {
    return this.http.get<SolicitudMovimiento[]>(`${this.baseUrl}/noleidas`);
  }

  // 🧾 Obtener todas las solicitudes normales de la empresa
  getSolicitudesEmpresa(): Observable<SolicitudMovimiento[]> {
    return this.http.get<SolicitudMovimiento[]>(`${this.baseUrl}/empresa`);
  }

  // ✅ Resolver una solicitud normal (aceptar o rechazar)
  resolverSolicitud(id: number, aceptar: boolean, respuesta: string): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}/aprobar`, {
      aceptar,
      respuestaAdmin: respuesta
    });
  }

}
