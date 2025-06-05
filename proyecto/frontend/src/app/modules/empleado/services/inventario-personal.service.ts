import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { InventarioPersonal } from 'src/app/core/interfaces/inventario-personal';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { PRODUCTO_ENDPOINT } from 'src/app/core/constants/constants';
import { MovimientoSalidaDto } from 'src/app/core/interfaces/MovimientoSalidaDto';

@Injectable({
  providedIn: 'root'
})
export class InventarioPersonalService {

  private miInventarioUrl = PRODUCTO_ENDPOINT + '/mi-inventario';
  private salidaPersonalUrl = PRODUCTO_ENDPOINT + '/salida-personal';

  constructor(private http: HttpClient) { }

  getInventarioPersonal(): Observable<InventarioPersonal[]> {
    return this.http.get<InventarioPersonal[]>(this.miInventarioUrl).pipe(
      tap(res => console.log('📦 Inventario personal recibido:', res)),
      catchError(err => {
        console.error('❌ Error al obtener inventario personal:', err);
        return of([]);
      })
    );
  }

  reducirCantidadInventarioPersonal(productoId: number, cantidad: number): Observable<any> {
    const url = `${PRODUCTO_ENDPOINT}/mi-inventario/${productoId}/reducir?cantidad=${cantidad}`;
    console.log(`📤 Enviando reducción → productoId=${productoId}, cantidad=${cantidad}`);
    return this.http.put(url, {}).pipe(
      tap(() => console.log('✅ Cantidad reducida correctamente')),
      catchError(err => {
        console.error('❌ Error al reducir cantidad del inventario personal:', err);
        return of();
      })
    );
  }

}
