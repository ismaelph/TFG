import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { PRODUCTO_ENDPOINT } from 'src/app/core/constants/constants';
import { Observable } from 'rxjs';
import { Producto } from 'src/app/core/interfaces/producto';

@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private apiUrl = PRODUCTO_ENDPOINT;

  constructor(private http: HttpClient) {}

  getProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.apiUrl);
  }

  getProductoById(id: number): Observable<Producto> {
    return this.http.get<Producto>(`${this.apiUrl}/${id}`);
  }

  crearProducto(producto: Producto): Observable<Producto> {
    return this.http.post<Producto>(this.apiUrl, producto);
  }

  actualizarProducto(id: number, producto: Producto): Observable<Producto> {
    return this.http.put<Producto>(`${this.apiUrl}/${id}`, producto);
  }

  eliminarProducto(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
