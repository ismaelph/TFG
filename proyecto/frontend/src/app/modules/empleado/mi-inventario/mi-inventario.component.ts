import { Component, OnInit } from '@angular/core';
import { InventarioPersonal } from 'src/app/core/interfaces/inventario-personal';
import { MovimientoProducto } from 'src/app/core/interfaces/movimiento-producto';
import Swal from 'sweetalert2';
import { MovimientoProductoService } from '../services/movimiento-producto.service';

@Component({
  selector: 'app-mi-inventario',
  templateUrl: './mi-inventario.component.html',
  styleUrls: ['./mi-inventario.component.css']
})
export class MiInventarioComponent implements OnInit {
  inventario: InventarioPersonal[] = [];
  inventarioPaginado: InventarioPersonal[] = [];
  cantidadEliminar: { [productoId: number]: number } = {};
  sliderActivo: { [productoId: number]: boolean } = {};

  paginaActual = 1;
  itemsPorPagina = 5;

  columnas = ['nombre', 'cantidad', 'categoriaNombre', 'proveedorNombre'];
  encabezados = ['Producto', 'Cantidad', 'Categoría', 'Proveedor'];

  constructor(private movimientoService: MovimientoProductoService) {}

  ngOnInit(): void {
    this.cargarInventario();
  }

  cargarInventario(): void {
    this.movimientoService.getInventarioPersonal().subscribe({
      next: data => {
        this.inventario = data;
        this.paginar();
        data.forEach(p => {
          this.cantidadEliminar[p.productoId] = 1;
          this.sliderActivo[p.productoId] = false;
        });
      },
      error: err => {
        console.error('Error al cargar mi inventario:', err);
      }
    });
  }

  paginar(): void {
    const inicio = (this.paginaActual - 1) * this.itemsPorPagina;
    const fin = inicio + this.itemsPorPagina;
    this.inventarioPaginado = this.inventario.slice(inicio, fin);
  }

  toggleSlider(id: number): void {
    this.sliderActivo[id] = !this.sliderActivo[id];
  }

  sumarCantidad(id: number): void {
    if (this.cantidadEliminar[id] < this.getCantidadDisponible(id)) {
      this.cantidadEliminar[id]++;
    }
  }

  restarCantidad(id: number): void {
    if (this.cantidadEliminar[id] > 1) {
      this.cantidadEliminar[id]--;
    }
  }

  getCantidadDisponible(id: number): number {
    return this.inventario.find(p => p.productoId === id)?.cantidad || 1;
  }

  confirmarEliminar(producto: InventarioPersonal): void {
  const cantidad = this.cantidadEliminar[producto.productoId];

  if (cantidad < 1 || cantidad > producto.cantidad) {
    Swal.fire('Error', 'Cantidad inválida', 'error');
    return;
  }

  const mov: Partial<MovimientoProducto> = {
    productoId: producto.productoId,
    tipo: 'SALIDA',
    cantidad
  };

  this.movimientoService.registrarMovimiento(mov).subscribe({
    next: () => {
      Swal.fire('Eliminado', `Se eliminaron ${cantidad} unidad(es) de "${producto.nombre}"`, 'success');
      this.cargarInventario();
    },
    error: () => {
      Swal.fire('Error', 'No se pudo eliminar el producto', 'error');
    }
  });
}

}
