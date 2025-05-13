import { Component, OnInit } from '@angular/core';
import { Producto } from 'src/app/core/interfaces/producto';
import { Categoria } from 'src/app/core/interfaces/categoria';
import { Proveedor } from 'src/app/core/interfaces/proveedor';
import { ProductoService } from '../../services/producto.service';
import { CategoriaService } from '../../services/categoria.service';
import { ProveedorService } from '../../services/proveedor.service';
import { Router } from '@angular/router';
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
  proveedor: Proveedor[] = [];

  categoriaSeleccionada: number | null = null;
  searchTerm: string = '';
  ordenAscendente: boolean = true;

  currentPage: number = 1;
  itemsPerPage: number = 10;

  constructor(
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    private proveedorService: ProveedorService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cargarCategorias();
    this.cargarProveedores();
    this.cargarProductos();
  }

  cargarProductos(): void {
    this.productoService.getProductos().subscribe({
      next: (data) => {
        this.productos = data;
        this.filtrarYOrdenar();
      }
    });
  }

  cargarCategorias(): void {
    this.categoriaService.getCategorias().subscribe({
      next: (data) => this.categorias = data
    });
  }

  cargarProveedores(): void {
    this.proveedorService.getProveedores().subscribe({
      next: (data) => this.proveedor = data
    });
  }

  filtrarYOrdenar(): void {
    const termino = this.searchTerm.toLowerCase();
    this.filtrados = this.productos
      .filter(p =>
        p.nombre.toLowerCase().includes(termino) &&
        (this.categoriaSeleccionada === null || p.categoriaId === this.categoriaSeleccionada)
      )
      .sort((a, b) => {
        const comp = a.nombre.localeCompare(b.nombre);
        return this.ordenAscendente ? comp : -comp;
      });
  }

  actualizarFiltro(): void {
    this.currentPage = 1;
    this.filtrarYOrdenar();
  }

  cambiarOrden(): void {
    this.ordenAscendente = !this.ordenAscendente;
    this.filtrarYOrdenar();
  }

  cambiarPagina(pagina: number): void {
    this.currentPage = pagina;
  }

  seleccionarCategoria(id: string): void {
    this.categoriaSeleccionada = id === 'none' ? null : +id;
    this.actualizarFiltro();
  }

  get productosPaginados(): Producto[] {
    const start = (this.currentPage - 1) * this.itemsPerPage;
    return this.filtrados.slice(start, start + this.itemsPerPage);
  }

  getNombreCategoriaById(id: number | undefined): string {
    const cat = this.categorias.find(c => c.id === id);
    return cat ? cat.nombre : 'Sin categoría';
  }

  getNombreProveedorById(id: number | undefined): string {
    const prov = this.proveedor.find(p => p.id === id);
    return prov ? prov.nombre : 'Sin proveedor';
  }

  editarProducto(id: number): void {
    this.router.navigate(['/empresa/producto/form', id]);
  }

  confirmarEliminacion(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'Esta acción eliminará el producto.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#6b7280',
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then(result => {
      if (result.isConfirmed) {
        this.eliminarProducto(id);
      }
    });
  }

  eliminarProducto(id: number): void {
    this.productoService.eliminarProducto(id).subscribe({
      next: () => {
        Swal.fire('Eliminado', 'El producto fue eliminado correctamente.', 'success');
        this.cargarProductos();
      },
      error: () => {
        Swal.fire('Error', 'No se pudo eliminar el producto.', 'error');
      }
    });
  }
}
