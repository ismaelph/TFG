import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { InventarioPersonal } from 'src/app/core/interfaces/inventario-personal';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { PRODUCTO_ENDPOINT } from 'src/app/core/constants/constants';

@Injectable({
  providedIn: 'root'
})
export class InventarioPersonalService {

  private miInventarioUrl = PRODUCTO_ENDPOINT + '/mi-inventario';

  constructor(private http: HttpClient) {}

  getInventarioPersonal(): Observable<InventarioPersonal[]> {
    return this.http.get<InventarioPersonal[]>(this.miInventarioUrl).pipe(
      tap(res => console.log('📦 Inventario personal recibido:', res)),
      catchError(err => {
        console.error('❌ Error al obtener inventario personal:', err);
        return of([]);
      })
    );
  }

  eliminarProducto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.miInventarioUrl}/${id}`).pipe(
      tap(() => console.log(`✅ Producto ${id} eliminado`)),
      catchError(err => {
        console.error(`❌ Error al eliminar producto ${id}:`, err);
        return of();
      })
    );
  }
}
