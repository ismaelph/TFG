import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Producto } from 'src/app/core/interfaces/producto';
import { Observable, of } from 'rxjs';
import { tap, catchError } from 'rxjs/operators';
import { PRODUCTO_ENDPOINT } from 'src/app/core/constants/constants';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {

  private baseUrl = PRODUCTO_ENDPOINT;

  constructor(private http: HttpClient) {}

  getTodos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.baseUrl).pipe(
      tap(productos => console.log('📦 Productos cargados:', productos)),
      catchError(err => {
        console.error('❌ Error al obtener productos:', err);
        return of([]);
      })
    );
  }

  getById(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.baseUrl}/${id}`).pipe(
      tap(producto => console.log(`🔍 Producto ID ${id}:`, producto)),
      catchError(err => {
        console.error(`❌ Error al obtener producto ID ${id}:`, err);
        return of({} as Producto);
      })
    );
  }

  crear(producto: Producto): Observable<Producto> {
    return this.http.post<Producto>(this.baseUrl, producto).pipe(
      tap(res => console.log('✅ Producto creado:', res)),
      catchError(err => {
        console.error('❌ Error al crear producto:', err);
        return of({ ...producto, id: -1 });
      })
    );
  }

  editar(id: number, producto: Producto): Observable<Producto> {
    return this.http.put<Producto>(`${this.baseUrl}/${id}`, producto).pipe(
      tap(res => console.log(`🛠 Producto ID ${id} editado:`, res)),
      catchError(err => {
        console.error(`❌ Error al editar producto ID ${id}:`, err);
        return of(producto);
      })
    );
  }

  eliminar(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`).pipe(
      tap(() => console.log(`🗑 Producto eliminado: ID ${id}`)),
      catchError(err => {
        console.error(`❌ Error al eliminar producto ID ${id}:`, err);
        return of(null);
      })
    );
  }

  getProductos(): Observable<Producto[]> {
  return this.http.get<Producto[]>(`${this.baseUrl}`);
}

}
