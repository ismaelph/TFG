import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SolicitudPersonalizada } from 'src/app/core/interfaces/SolicitudPersonalizada';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SolicitudPersonalizadaService {
  private baseUrl = `${environment.apiUrl}/api/solicitudes/personalizada`;

  constructor(private http: HttpClient) {}

  crearSolicitud(dto: SolicitudPersonalizada): Observable<any> {
    return this.http.post(`${this.baseUrl}/crear`, dto);
  }

  // futuro:
  // obtenerMisSolicitudes(): Observable<SolicitudPersonalizada[]> { ... }
}
