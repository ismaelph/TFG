import { Component, OnInit } from '@angular/core';
import { Producto } from 'src/app/core/interfaces/producto';
import Swal from 'sweetalert2';
import { ProductoService } from '../services/producto.service';
import { SolicitudMovimientoService } from '../services/solicitud-movimiento.service';
import { SolicitudMovimiento } from 'src/app/core/interfaces/SolicitudMovimiento';

@Component({
  selector: 'app-inventario',
  templateUrl: './inventario.component.html',
  styleUrls: ['./inventario.component.css']
})
export class InventarioComponent implements OnInit {

  productos: Producto[] = [];
  productosPaginados: Producto[] = [];

  modalAbierto = false;
  productoSeleccionado: Producto | null = null;
  cantidad: number = 1;
  motivo: string = '';

  paginaActual: number = 1;
  itemsPorPagina: number = 5;

  constructor(
    private productoService: ProductoService,
    private solicitudMovimientoService: SolicitudMovimientoService
  ) { }

  ngOnInit(): void {
    console.log('🚀 Componente inventario cargado.');
    this.cargarProductos();
  }

  cargarProductos(): void {
    console.log('🔄 Cargando productos desde backend...');
    this.productoService.getProductos().subscribe({
      next: (data: Producto[]) => {
        this.productos = data;
        this.paginar();
        console.log('📦 Productos cargados:', this.productos);
      },
      error: (err: any) => {
        console.error('❌ Error al cargar productos:', err);
      }
    });
  }

  paginar(): void {
    const start = (this.paginaActual - 1) * this.itemsPorPagina;
    const end = start + this.itemsPorPagina;
    this.productosPaginados = this.productos.slice(start, end);
    console.log(`📄 Página ${this.paginaActual}: productos del ${start} al ${end}`);
  }

  abrirModal(producto: Producto): void {
    console.log('🟢 Abriendo modal para producto:', producto);
    this.productoSeleccionado = producto;
    this.cantidad = 1;
    this.motivo = '';
    this.modalAbierto = true;
  }

  cancelar(): void {
    console.log('🛑 Modal cancelado');
    this.modalAbierto = false;
    this.productoSeleccionado = null;
  }

  enviarSolicitud(): void {
    try {
      if (!this.productoSeleccionado || !this.motivo.trim() || this.cantidad < 1) {
        console.warn('⚠️ Solicitud inválida. Faltan campos.');
        Swal.fire('Error', 'Todos los campos son obligatorios.', 'error');
        return;
      }

      const solicitud: SolicitudMovimiento = {
        productoId: this.productoSeleccionado.id!,
        cantidadSolicitada: this.cantidad,
        motivo: this.motivo
      };

      console.log('📤 Enviando solicitud:', solicitud);

      this.solicitudMovimientoService.crearSolicitud(solicitud).subscribe({
        next: () => {
          console.log('✅ Solicitud enviada correctamente.');
          Swal.fire('Éxito', 'Solicitud enviada correctamente.', 'success');
          this.cancelar();
        },
        error: (err: any) => {
          console.error('❌ Error HTTP al enviar solicitud:', err);
          Swal.fire('Error', 'No se pudo enviar la solicitud.', 'error');
        }
      });
    } catch (e) {
      console.error('❌ Error inesperado en enviarSolicitud():', e);
      Swal.fire('Error', 'Error interno en la solicitud.', 'error');
    }
  }
}
