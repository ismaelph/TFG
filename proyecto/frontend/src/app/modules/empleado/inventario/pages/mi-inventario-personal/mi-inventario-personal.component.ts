import { Component, OnInit } from '@angular/core';
import { InventarioPersonal } from 'src/app/core/interfaces/inventario-personal';
import { InventarioPersonalService } from '../../../services/inventario-personal.service';

@Component({
  selector: 'app-mi-inventario-personal',
  templateUrl: './mi-inventario-personal.component.html',
  styleUrls: ['./mi-inventario-personal.component.css']
})
export class MiInventarioPersonalComponent implements OnInit {
  productos: InventarioPersonal[] = [];
  productosPaginados: InventarioPersonal[] = [];
  pageSize = 5;
  paginaActual = 1;

  constructor(private inventarioService: InventarioPersonalService) {}

  ngOnInit(): void {
    this.cargarInventario();
  }

  cargarInventario(): void {
    try {
      this.inventarioService.getInventarioPersonal().subscribe({
        next: (data) => {
          this.productos = data;
          this.actualizarPaginacion();
          console.log('📦 Inventario personal cargado:', this.productos);
        },
        error: (err) => {
          console.error('❌ Error al cargar inventario personal:', err);
        }
      });
    } catch (e) {
      console.error('🚨 Error inesperado:', e);
    }
  }

  actualizarPaginacion(): void {
    const inicio = (this.paginaActual - 1) * this.pageSize;
    this.productosPaginados = this.productos.slice(inicio, inicio + this.pageSize);
  }

  cambiarPagina(pagina: number): void {
    this.paginaActual = pagina;
    this.actualizarPaginacion();
  }

  eliminarProducto(id: number): void {
    this.inventarioService.eliminarProducto(id).subscribe({
      next: () => {
        console.log(`✅ Producto ${id} eliminado`);
        this.cargarInventario(); // Recargar inventario después de eliminar
      },
      error: (err) => {
        console.error(`❌ Error al eliminar producto ${id}:`, err);
      }
    });
  }
}
