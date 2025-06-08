import { Component, OnInit } from '@angular/core';
import { MovimientoProducto } from 'src/app/core/interfaces/movimiento-producto';
import { MovimientoProductoService } from '../../services/movimiento-producto.service';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

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
  searchUsuario: string = '';
  tipoSeleccionado: string = '';
  fechaDesde: string = '';
  fechaHasta: string = '';
  usuariosUnicos: string[] = [];

  constructor(private movimientoService: MovimientoProductoService) {}

  ngOnInit(): void {
    this.cargarMovimientos();
  }

  cargarMovimientos(): void {
    this.movimientoService.getMovimientosDeEmpresa().subscribe({
      next: (data) => {
        this.movimientos = data;
        this.usuariosUnicos = [...new Set(data.map(m => m.usuarioNombre ?? '').filter((n): n is string => n !== ''))];
        this.actualizarFiltro();
      },
      error: (err) => {
        console.error('Error al cargar movimientos:', err);
      }
    });
  }

  actualizarFiltro(): void {
    this.filtrados = this.movimientos.filter(m => {
      const nombre = m.productoNombre?.toLowerCase() ?? '';
      const usuario = m.usuarioNombre?.toLowerCase() ?? '';
      const tipo = m.tipo ?? '';
      const fechaRaw = m.fecha ?? '';

      const coincideTexto = nombre.includes(this.searchTerm.toLowerCase());
      const coincideUsuario = usuario.includes(this.searchUsuario.toLowerCase());
      const coincideTipo = this.tipoSeleccionado ? tipo === this.tipoSeleccionado : true;

      const fecha = new Date(fechaRaw || '1970-01-01T00:00:00');
      const desde = this.fechaDesde ? new Date(this.fechaDesde) : null;
      const hasta = this.fechaHasta ? new Date(this.fechaHasta) : null;

      const dentroDeRango = (!desde || fecha >= desde) && (!hasta || fecha <= hasta);

      return coincideTexto && coincideUsuario && coincideTipo && dentroDeRango;
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
    this.searchUsuario = '';
    this.tipoSeleccionado = '';
    this.fechaDesde = '';
    this.fechaHasta = '';
    this.actualizarFiltro();
  }

  exportarFiltradosComoPDF(): void {
    const doc = new jsPDF();

    const fechaActual = new Date().toLocaleString('es-ES');
    doc.setFontSize(14);
    doc.text(`Exportación de Movimientos – ${fechaActual}`, 14, 35);

    const datos = this.filtrados.map(m => [
      m.productoNombre ?? '—',
      m.tipo ?? '—',
      m.cantidad ?? 0,
      m.usuarioNombre ?? '—',
      m.fecha
        ? new Date(m.fecha).toLocaleString('es-ES', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
          })
        : '—'
    ]);

    autoTable(doc, {
      head: [this.headers],
      body: datos,
      startY: 45,
      styles: {
        fontSize: 9,
        cellPadding: 3,
        halign: 'center'
      },
      headStyles: {
        fillColor: [107, 178, 167], // verde suave
        textColor: 255,
        fontStyle: 'bold'
      },
      margin: { top: 45 }
    });

    // Insertar logo2.png
    const logo = new Image();
    logo.src = 'assets/images/logo2.png';

    logo.onload = () => {
      doc.addImage(logo, 'PNG', 150, 8, 40, 20); // posX, posY, width, height
      doc.save('movimientos-filtrados.pdf');
    };
  }
}
