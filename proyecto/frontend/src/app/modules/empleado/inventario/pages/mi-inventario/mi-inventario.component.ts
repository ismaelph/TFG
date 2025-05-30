import { Component, OnInit } from '@angular/core';
import { Producto } from 'src/app/core/interfaces/producto';
import { ProductoService } from '../../../services/producto.service';

@Component({
  selector: 'app-mi-inventario',
  templateUrl: './mi-inventario.component.html',
  styleUrls: ['./mi-inventario.component.css']
})
export class MiInventarioComponent implements OnInit {
  productos: Producto[] = [];
  filtrados: Producto[] = [];

  searchTerm: string = '';
  currentPage: number = 1;
  pageSize: number = 5;

  mostrarModal: boolean = false;
  productoSeleccionado: Producto | null = null;

  constructor(private productoService: ProductoService) {}

  ngOnInit(): void {
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.productoService.getProductos().subscribe({
      next: (data) => {
        this.productos = data;
        this.actualizarFiltro();
      },
      error: (err) => console.error('Error al cargar productos:', err)
    });
  }

  actualizarFiltro(): void {
    const buscado = this.searchTerm.toLowerCase();
    this.filtrados = this.productos.filter(p =>
      p.nombre.toLowerCase().includes(buscado)
    );
  }

  get productosPaginados(): Producto[] {
    const start = (this.currentPage - 1) * this.pageSize;
    return this.filtrados.slice(start, start + this.pageSize);
  }

  cambiarPagina(pagina: number): void {
    this.currentPage = pagina;
  }

  limpiarBusqueda(): void {
    this.searchTerm = '';
    this.actualizarFiltro();
  }

  abrirModal(producto: Producto): void {
    this.productoSeleccionado = producto;
    this.mostrarModal = true;
  }

  cerrarModal(): void {
    this.mostrarModal = false;
    this.productoSeleccionado = null;
  }
}
