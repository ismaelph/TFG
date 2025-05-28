import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MovimientoProducto } from 'src/app/core/interfaces/movimiento-producto';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { MOVIMIENTO_ENDPOINT } from 'src/app/core/constants/constants';

@Injectable({
  providedIn: 'root'
})
export class MovimientoProductoService {

  private baseUrl = MOVIMIENTO_ENDPOINT;

  constructor(private http: HttpClient) {}

  getTodos(): Observable<MovimientoProducto[]> {
    return this.http.get<MovimientoProducto[]>(this.baseUrl).pipe(
      tap(movs => console.log('📦 Movimientos de empresa:', movs)),
      catchError(err => {
        console.error('❌ Error al obtener movimientos de empresa:', err);
        return of([]);
      })
    );
  }

  getMisMovimientos(): Observable<MovimientoProducto[]> {
    return this.http.get<MovimientoProducto[]>(`${this.baseUrl}/mios`).pipe(
      tap(mios => console.log('📥 Movimientos personales:', mios)),
      catchError(err => {
        console.error('❌ Error al obtener mis movimientos:', err);
        return of([]);
      })
    );
  }

  getByProductoId(id: number): Observable<MovimientoProducto[]> {
    return this.http.get<MovimientoProducto[]>(`${this.baseUrl}/producto/${id}`).pipe(
      tap(data => console.log(`🔎 Movimientos del producto ${id}:`, data)),
      catchError(err => {
        console.error(`❌ Error al obtener movimientos del producto ${id}:`, err);
        return of([]);
      })
    );
  }

  save(mov: MovimientoProducto): Observable<MovimientoProducto> {
    return this.http.post<MovimientoProducto>(this.baseUrl, mov).pipe(
      tap(nuevo => console.log('✅ Movimiento guardado:', nuevo)),
      catchError(err => {
        console.error('❌ Error al guardar movimiento:', err);
        return of({ ...mov, id: -1 });
      })
    );
  }

  getById(id: number): Observable<MovimientoProducto> {
    return this.http.get<MovimientoProducto>(`${this.baseUrl}/${id}`).pipe(
      tap(mov => console.log(`🔍 Movimiento ID ${id}:`, mov)),
      catchError(err => {
        console.error(`❌ Error al buscar movimiento con ID ${id}:`, err);
        return of({} as MovimientoProducto);
      })
    );
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`).pipe(
      tap(() => console.log(`🗑 Movimiento eliminado: ID ${id}`)),
      catchError(err => {
        console.error(`❌ Error al eliminar movimiento con ID ${id}:`, err);
        return of(null);
      })
    );
  }
}
