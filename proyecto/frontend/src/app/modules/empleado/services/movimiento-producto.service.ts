import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MovimientoProducto } from 'src/app/core/interfaces/movimiento-producto';
import { InventarioPersonal } from 'src/app/core/interfaces/inventario-personal';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MovimientoProductoService {
  private apiUrl = `${environment.apiUrl}/api/movimientos`;
  private inventarioUrl = `${environment.apiUrl}/api/productos/mi-inventario`;

  constructor(private http: HttpClient) { }

  registrarMovimiento(movimiento: Partial<MovimientoProducto>): Observable<MovimientoProducto> {
    return this.http.post<MovimientoProducto>(this.apiUrl, movimiento);
  }

  getMisMovimientos(): Observable<MovimientoProducto[]> {
    return this.http.get<MovimientoProducto[]>(`${this.apiUrl}/mios`);
  }

  getPorProducto(id: number): Observable<MovimientoProducto[]> {
    return this.http.get<MovimientoProducto[]>(`${this.apiUrl}/producto/${id}`);
  }

  getMovimientosDeEmpresa(): Observable<MovimientoProducto[]> {
    return this.http.get<MovimientoProducto[]>(this.apiUrl);
  }

  getInventarioPersonal(): Observable<InventarioPersonal[]> {
    return this.http.get<InventarioPersonal[]>(this.inventarioUrl);
  }

  transferirProducto(movimiento: { productoId: number; cantidad: number }): Observable<any> {
    return this.http.post(`${this.apiUrl}/transferir`, movimiento);
  }
}
