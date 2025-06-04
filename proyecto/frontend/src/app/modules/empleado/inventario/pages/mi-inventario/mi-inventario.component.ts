import { Component, OnInit } from '@angular/core';
import { Producto } from 'src/app/core/interfaces/producto';
import { Categoria } from 'src/app/core/interfaces/categoria';
import { Proveedor } from 'src/app/core/interfaces/proveedor';
import { ProductoService } from '../../../services/producto.service';
import { CategoriaService } from 'src/app/modules/empresa-admin/services/categoria.service';
import { ProveedorService } from 'src/app/modules/empresa-admin/services/proveedor.service';

@Component({
  selector: 'app-mi-inventario',
  templateUrl: './mi-inventario.component.html',
  styleUrls: ['./mi-inventario.component.css']
})
export class MiInventarioComponent implements OnInit {
  productos: Producto[] = [];
  filtrados: Producto[] = [];

  categorias: Categoria[] = [];
  proveedores: Proveedor[] = [];

  categoriaSeleccionada: number | null = null;
  proveedorSeleccionado: number | null = null;
  searchTerm: string = '';
  ordenAscendente: boolean = true;

  currentPage: number = 1;
  pageSize: number = 6;

  mostrarModalSolicitud = false;
  mostrarModalInfo = false;
  productoSeleccionado: Producto | null = null;

  constructor(
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    private proveedorService: ProveedorService
  ) { }

  ngOnInit(): void {
    this.cargarProductos();
    this.cargarCategorias();
    this.cargarProveedores();
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

  cargarCategorias(): void {
    this.categoriaService.getCategorias().subscribe({
      next: (data) => this.categorias = data,
      error: (err) => console.error('Error al cargar categorías:', err)
    });
  }

  cargarProveedores(): void {
    this.proveedorService.getProveedores().subscribe({
      next: (data) => this.proveedores = data,
      error: (err) => console.error('Error al cargar proveedores:', err)
    });
  }

  actualizarFiltro(): void {
    const term = this.searchTerm.toLowerCase();
    this.filtrados = this.productos.filter(p => {
      const coincideNombre = p.nombre.toLowerCase().includes(term);
      const coincideCategoria = !this.categoriaSeleccionada || p.categoriaId === this.categoriaSeleccionada;
      const coincideProveedor = !this.proveedorSeleccionado || p.proveedorId === this.proveedorSeleccionado;
      return coincideNombre && coincideCategoria && coincideProveedor;
    });

    this.ordenarProductos();
  }

  ordenarProductos(): void {
    this.filtrados.sort((a, b) => {
      const aNombre = a.nombre.toLowerCase();
      const bNombre = b.nombre.toLowerCase();
      return this.ordenAscendente ? aNombre.localeCompare(bNombre) : bNombre.localeCompare(aNombre);
    });
  }

  cambiarOrden(): void {
    this.ordenAscendente = !this.ordenAscendente;
    this.ordenarProductos();
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

  abrirModalSolicitud(id: number): void {
    const producto = this.productos.find(p => p.id === id);
    if (producto) {
      this.productoSeleccionado = producto;
      this.mostrarModalSolicitud = true;
    }
  }

  abrirModalInfo(id: number): void {
    const producto = this.productos.find(p => p.id === id);
    if (producto) {
      this.productoSeleccionado = producto;
      this.mostrarModalInfo = true;
    }
  }

  cerrarModales(): void {
    this.mostrarModalSolicitud = false;
    this.mostrarModalInfo = false;
    this.productoSeleccionado = null;
  }

  mostrarModalPersonalizada = false;

  abrirModalPersonalizada(): void {
    this.mostrarModalPersonalizada = true;
  }

  cerrarModalesPersonal(): void {
    this.mostrarModalPersonalizada = false;
  }

}
