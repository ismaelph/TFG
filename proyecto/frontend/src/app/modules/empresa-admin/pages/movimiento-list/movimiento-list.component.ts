import { Component, OnInit } from '@angular/core';
import { MovimientoProducto } from 'src/app/core/interfaces/movimiento-producto';
import { MovimientoProductoService } from '../../services/movimiento-producto.service';

@Component({
  selector: 'app-movimiento-list',
  templateUrl: './movimiento-list.component.html',
  styleUrls: ['./movimiento-list.component.css']
})
export class MovimientoListComponent implements OnInit {
  movimientos: MovimientoProducto[] = [];
  filtrados: MovimientoProducto[] = [];

  headers: string[] = ['Producto', 'Tipo', 'Cantidad', 'Usuario', 'Fecha'];
  tablaDatos: any[] = [];

  searchTerm: string = '';
  tipoSeleccionado: string = '';
  fechaDesde: string = '';
  fechaHasta: string = '';

  constructor(private movimientoService: MovimientoProductoService) { }

  ngOnInit(): void {
    this.cargarMovimientos();
  }

  cargarMovimientos(): void {
    this.movimientoService.getMovimientosDeEmpresa().subscribe({
      next: (data) => {
        this.movimientos = data;
        this.actualizarFiltro();
      },
      error: (err) => {
        console.error('Error al cargar movimientos:', err);
      }
    });
  }

  actualizarFiltro(): void {
    this.filtrados = this.movimientos.filter(m => {
      const nombre = m.productoNombre ?? '';
      const tipo = m.tipo ?? '';
      const fechaRaw = m.fecha ?? '';

      const coincideTexto = nombre.toLowerCase().includes(this.searchTerm.toLowerCase());
      const coincideTipo = this.tipoSeleccionado ? tipo === this.tipoSeleccionado : true;

      const fecha = new Date(fechaRaw || '1970-01-01T00:00:00');
      const desde = this.fechaDesde ? new Date(this.fechaDesde) : null;
      const hasta = this.fechaHasta ? new Date(this.fechaHasta) : null;

      const dentroDeRango = (!desde || fecha >= desde) && (!hasta || fecha <= hasta);

      return coincideTexto && coincideTipo && dentroDeRango;
    });

    this.tablaDatos = this.filtrados.map(m => ({
      Producto: m.productoNombre ?? '—',
      Tipo: m.tipo ?? '—',
      Cantidad: m.cantidad ?? 0,
      Usuario: m.usuarioNombre ?? '—',
      Fecha: m.fecha
        ? new Date(m.fecha).toLocaleString('es-ES', {
          day: '2-digit',
          month: '2-digit',
          year: 'numeric',
          hour: '2-digit',
          minute: '2-digit'
        })
        : '—'
    }));
  }

  limpiarFiltros(): void {
    this.searchTerm = '';
    this.tipoSeleccionado = '';
    this.fechaDesde = '';
    this.fechaHasta = '';
    this.actualizarFiltro();
  }
}
