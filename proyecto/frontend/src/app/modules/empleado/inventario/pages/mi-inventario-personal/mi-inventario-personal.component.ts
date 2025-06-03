import { Component, OnInit } from '@angular/core';
import { InventarioPersonal } from 'src/app/core/interfaces/inventario-personal';
import { InventarioPersonalService } from '../../../services/inventario-personal.service';

@Component({
  selector: 'app-mi-inventario-personal',
  templateUrl: './mi-inventario-personal.component.html',
  styleUrls: ['./mi-inventario-personal.component.css']
})
export class MiInventarioPersonalComponent implements OnInit {
  headers: string[] = ['Nombre', 'Categoría', 'Proveedor', 'Cantidad', 'Ubicación'];
  rows: any[][] = [];

  inventario: InventarioPersonal[] = [];
  paginados: InventarioPersonal[] = [];

  cargando = true;

  currentPage: number = 1;
  pageSize: number = 6;

  constructor(private inventarioService: InventarioPersonalService) {}

  ngOnInit(): void {
    this.inventarioService.getInventarioPersonal().subscribe({
      next: (data) => {
        this.inventario = data;
        this.actualizarTabla();
        this.cargando = false;
      },
      error: (err) => {
        console.error('Error al cargar inventario personal:', err);
        this.cargando = false;
      }
    });
  }

  actualizarTabla(): void {
    const start = (this.currentPage - 1) * this.pageSize;
    this.paginados = this.inventario.slice(start, start + this.pageSize);
    this.rows = this.paginados.map(item => [
      item.nombre,
      item.categoria,
      item.proveedor,
      item.cantidad,
      item.ubicacion || '—'
    ]);
  }

  cambiarPagina(pagina: number): void {
    this.currentPage = pagina;
    this.actualizarTabla();
  }
}
