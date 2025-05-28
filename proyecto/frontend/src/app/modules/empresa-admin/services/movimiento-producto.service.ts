import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MovimientoProducto } from 'src/app/core/interfaces/movimiento-producto';
import { MOVIMIENTO_ENDPOINT } from 'src/app/core/constants/constants';

@Injectable({
  providedIn: 'root'
})
export class MovimientoProductoService {

  constructor(private http: HttpClient) {}

  /**
   * Obtiene los movimientos de la empresa del usuario autenticado
   */
  getMovimientosDeEmpresa(): Observable<MovimientoProducto[]> {
    return this.http.get<MovimientoProducto[]>(MOVIMIENTO_ENDPOINT);
  }
}
