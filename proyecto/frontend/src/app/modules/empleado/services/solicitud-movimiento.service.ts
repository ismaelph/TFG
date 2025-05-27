import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SolicitudMovimiento } from 'src/app/core/interfaces/SolicitudMovimiento';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SolicitudMovimientoService {
  private baseUrl = `${environment.apiUrl}/api/solicitudes/movimiento`;

  constructor(private http: HttpClient) {}

  crearSolicitud(dto: SolicitudMovimiento): Observable<any> {
    return this.http.post(`${this.baseUrl}/crear`, dto);
  }

  // futuro:
  // obtenerMisSolicitudes(): Observable<SolicitudMovimiento[]> { ... }
}
