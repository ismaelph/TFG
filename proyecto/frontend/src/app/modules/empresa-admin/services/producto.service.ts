import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PRODUCTO_ENDPOINT } from 'src/app/core/constants/constants';
import { Observable, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { Producto } from 'src/app/core/interfaces/producto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private apiUrl = PRODUCTO_ENDPOINT;

  constructor(private http: HttpClient) {}

  getProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.apiUrl).pipe(
      tap(res => console.log('📦 Productos cargados:', res)),
      catchError(err => {
        console.error('❌ Error al cargar productos:', err);
        return of([]);
      })
    );
  }

  getProductoById(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiUrl}/${id}`).pipe(
      tap(res => console.log('🔍 Producto encontrado:', res)),
      catchError(err => {
        console.error(`❌ Error al obtener producto ID ${id}:`, err);
        return of({} as Producto);
      })
    );
  }

  crearProducto(producto: Producto, imagen?: File): Observable<Producto> {
    const formData = new FormData();

    // Convertir el objeto producto a Blob con tipo JSON
    formData.append('producto', new Blob(
      [JSON.stringify(producto)],
      { type: 'application/json' }
    ));

    if (imagen) {
      formData.append('imagen', imagen);
    }

    return this.http.post<Producto>(this.apiUrl, formData).pipe(
      tap(res => console.log('✅ Producto creado:', res)),
      catchError(err => {
        console.error('❌ Error al crear producto:', err);
        return of({ id: -1 } as Producto);
      })
    );
  }

  actualizarProducto(id: number, producto: Producto, imagen?: File): Observable<Producto> {
    const formData = new FormData();

    formData.append('producto', new Blob(
      [JSON.stringify(producto)],
      { type: 'application/json' }
    ));

    if (imagen) {
      formData.append('imagen', imagen);
    }

    return this.http.put<Producto>(`${this.apiUrl}/${id}`, formData).pipe(
      tap(res => console.log(`🛠 Producto ID ${id} actualizado:`, res)),
      catchError(err => {
        console.error(`❌ Error al actualizar producto ID ${id}:`, err);
        return of({ id } as Producto);
      })
    );
  }

  eliminarProducto(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`).pipe(
      tap(() => console.log(`🗑 Producto eliminado: ID ${id}`)),
      catchError(err => {
        console.error(`❌ Error al eliminar producto ID ${id}:`, err);
        return of(null);
      })
    );
  }
}
