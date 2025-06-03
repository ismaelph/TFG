import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SolicitudPersonalizada } from 'src/app/core/interfaces/SolicitudPersonalizada';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SolicitudPersonalizadaService {
  private baseUrl = `${environment.apiUrl}/api/solicitudes/personalizada`;

  constructor(private http: HttpClient) {}

  // 🔄 Solicitudes personalizadas de la empresa del admin
  getSolicitudesEmpresa(): Observable<SolicitudPersonalizada[]> {
    return this.http.get<SolicitudPersonalizada[]>(`${this.baseUrl}/empresa`);
  }

  // 🔄 Solo las no leídas
  getSolicitudesNoLeidas(): Observable<SolicitudPersonalizada[]> {
    return this.http.get<SolicitudPersonalizada[]>(`${this.baseUrl}/noleidas`);
  }

  // ✅ Aprobar o rechazar
  resolverSolicitud(id: number, aceptar: boolean, respuesta: string): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}/aprobar`, {
      aceptar,
      respuestaAdmin: respuesta
    });
  }
}
