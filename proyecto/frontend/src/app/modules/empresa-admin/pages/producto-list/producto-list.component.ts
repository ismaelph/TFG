import { Component, OnInit } from '@angular/core';
import { Producto } from 'src/app/core/interfaces/producto';
import { Categoria } from 'src/app/core/interfaces/categoria';
import { Proveedor } from 'src/app/core/interfaces/proveedor';
import { ProductoService } from '../../services/producto.service';
import { CategoriaService } from '../../services/categoria.service';
import { ProveedorService } from '../../services/proveedor.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-producto-list',
  templateUrl: './producto-list.component.html',
  styleUrls: ['./producto-list.component.css']
})
export class ProductoListComponent implements OnInit {
  productos: Producto[] = [];
  filtrados: Producto[] = [];

  categorias: Categoria[] = [];
  proveedores: Proveedor[] = [];

  categoriaSeleccionada: number | null = null;
  proveedorSeleccionado: number | null = null;
  searchTerm: string = '';
  ordenAscendente: boolean = true;

  currentPage: number = 1;
  itemsPerPage: number = 10;

  mostrarModalCrear = false;
  mostrarModalEditar = false;
  mostrarModalVer = false;

  productoIdEditar: number | null = null;
  productoSeleccionado: Producto | null = null;

  constructor(
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    private proveedorService: ProveedorService
  ) {}

  ngOnInit(): void {
    this.cargarCategorias();
    this.cargarProveedores();
    this.cargarProductos();
  }

  cargarCategorias(): void {
    this.categoriaService.getCategorias().subscribe({
      next: (data) => this.categorias = data,
      error: (err) => {
        console.error('Error cargando categorías:', err);
        Swal.fire('Error', 'No se pudieron cargar las categorías', 'error');
      }
    });
  }

  cargarProveedores(): void {
    this.proveedorService.getProveedores().subscribe({
      next: (data) => this.proveedores = data,
      error: (err) => {
        console.error('Error cargando proveedores:', err);
        Swal.fire('Error', 'No se pudieron cargar los proveedores', 'error');
      }
    });
  }

  cargarProductos(): void {
    this.productoService.getProductos().subscribe({
      next: (data) => {
        this.productos = data;
        this.actualizarFiltro();
      },
      error: (err) => {
        console.error('Error al cargar productos:', err);
        Swal.fire('Error', 'No se pudieron cargar los productos', 'error');
      }
    });
  }

  actualizarFiltro(): void {
    this.currentPage = 1;
    this.filtrados = this.productos.filter(p => {
      const coincideTexto = p.nombre.toLowerCase().includes(this.searchTerm.toLowerCase());
      const coincideCategoria = !this.categoriaSeleccionada || p.categoriaId === this.categoriaSeleccionada;
      const coincideProveedor = !this.proveedorSeleccionado || p.proveedorId === this.proveedorSeleccionado;
      return coincideTexto && coincideCategoria && coincideProveedor;
    });

    this.ordenarProductos();
  }

  cambiarOrden(): void {
    this.ordenAscendente = !this.ordenAscendente;
    this.ordenarProductos();
  }

  ordenarProductos(): void {
    this.filtrados.sort((a, b) => {
      const nombreA = a.nombre.toLowerCase();
      const nombreB = b.nombre.toLowerCase();
      return this.ordenAscendente ? nombreA.localeCompare(nombreB) : nombreB.localeCompare(nombreA);
    });
  }

  get productosPaginados(): Producto[] {
    const start = (this.currentPage - 1) * this.itemsPerPage;
    return this.filtrados.slice(start, start + this.itemsPerPage);
  }

  abrirModalCrear(): void {
    this.mostrarModalCrear = true;
  }

  abrirModalEditar(id: number): void {
    this.productoIdEditar = id;
    this.mostrarModalEditar = true;
  }

  abrirModalVer(producto: Producto): void {
    this.productoSeleccionado = producto;
    this.mostrarModalVer = true;
  }

  cerrarModalVer(): void {
    this.mostrarModalVer = false;
    this.productoSeleccionado = null;
  }

  cerrarModales(): void {
    this.mostrarModalCrear = false;
    this.mostrarModalEditar = false;
    this.productoIdEditar = null;
    this.cargarProductos();
  }

  confirmarEliminacion(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción no se puede deshacer',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Sí, eliminar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.eliminarProducto(id);
      }
    });
  }

  eliminarProducto(id: number): void {
    this.productoService.eliminarProducto(id).subscribe({
      next: () => {
        this.productos = this.productos.filter(p => p.id !== id);
        this.actualizarFiltro();
        Swal.fire('Eliminado', 'El producto ha sido eliminado', 'success');
      },
      error: (err) => {
        console.error('Error al eliminar producto:', err);
        Swal.fire('Error', 'No se pudo eliminar el producto', 'error');
      }
    });
  }
}
