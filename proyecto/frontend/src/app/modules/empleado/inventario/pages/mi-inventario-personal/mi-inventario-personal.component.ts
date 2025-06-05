import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
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

  // Modal de información
  mostrarModalInfo = false;
  productoSeleccionado: InventarioPersonal | null = null;

  // Modal de eliminación
  mostrarModalEliminar = false;
  productoIdAEliminar: number | null = null;
  cantidadAEliminar: number = 1;

  constructor(
    private inventarioService: InventarioPersonalService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.cargarInventario();
  }

  cargarInventario(): void {
    this.inventarioService.getInventarioPersonal().subscribe({
      next: (data) => {
        this.productos = data;
        this.actualizarPaginacion();
        this.cdr.detectChanges();
      },
      error: (err) => console.error('❌ Error al cargar inventario:', err)
    });
  }

  actualizarPaginacion(): void {
    const inicio = (this.paginaActual - 1) * this.pageSize;
    this.productosPaginados = this.productos.slice(inicio, inicio + this.pageSize);
  }

  cambiarPagina(pagina: number): void {
    this.paginaActual = pagina;
    this.actualizarPaginacion();
  }

  abrirModalInfo(id: number): void {
    const producto = this.productos.find(p => p.productoId === id);
    if (producto) {
      this.productoSeleccionado = producto;
      this.mostrarModalInfo = true;
    }
  }

  abrirModalEliminar(id: number): void {
    this.productoIdAEliminar = id;
    this.cantidadAEliminar = 1;
    this.mostrarModalEliminar = true;
  }

  confirmarEliminarProducto(): void {
    if (!this.productoIdAEliminar || !this.cantidadAEliminar) return;

    console.log(`📤 Reduciendo cantidad del producto ID ${this.productoIdAEliminar} en ${this.cantidadAEliminar} unidades`);

    this.inventarioService.reducirCantidadInventarioPersonal(this.productoIdAEliminar, this.cantidadAEliminar).subscribe({
      next: () => {
        console.log('✅ Cantidad reducida con éxito');
        this.mostrarModalEliminar = false;
        this.productoIdAEliminar = null;
        this.cantidadAEliminar = 1; // Restablecer valor por defecto
        this.cargarInventario();    // Recargar la lista
      },
      error: (err) => {
        console.error('❌ Error al reducir la cantidad del producto:', err);
      }
    });
  }


  cerrarModales(): void {
    this.mostrarModalInfo = false;
    this.mostrarModalEliminar = false;
    this.productoSeleccionado = null;
    this.productoIdAEliminar = null;
  }

  trackById(index: number, item: InventarioPersonal): number {
    return item.productoId;
  }
}
