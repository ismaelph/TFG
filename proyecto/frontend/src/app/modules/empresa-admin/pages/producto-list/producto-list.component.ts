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
  proveedores: Proveedor[] = [];

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
  ) { }

  ngOnInit(): void {
    this.cargarCategorias();
    this.cargarProveedores();
    this.cargarProductos();
  }

  cargarCategorias(): void {
    this.categoriaService.getCategorias().subscribe({
      next: (data) => {
        this.categorias = data;
        console.log('Categorías cargadas:', data);
      },
      error: (err) => {
        console.error('Error cargando categorías:', err);
      }
    });
  }

  cargarProveedores(): void {
    this.proveedorService.getProveedores().subscribe({
      next: (data) => {
        this.proveedores = data;
        console.log('Proveedores cargados:', data);
      },
      error: (err) => {
        console.error('Error cargando proveedores:', err);
      }
    });
  }

  cargarProductos(): void {
    this.productoService.getProductos().subscribe({
      next: (data) => {
        this.productos = data;
        console.log('Productos recibidos:', data);
        this.actualizarFiltro();
      },
      error: (err) => {
        console.error('Error al cargar productos:', err);
      }
    });
  }

  actualizarFiltro(): void {
    this.filtrados = this.productos.filter(p => {
      const coincideTexto = p.nombre.toLowerCase().includes(this.searchTerm.toLowerCase());
      const coincideCategoria = !this.categoriaSeleccionada || p.categoriaId === this.categoriaSeleccionada;
      return coincideTexto && coincideCategoria;
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

  getNombreCategoriaById(id: number | null | undefined): string {
  const cat = this.categorias.find(c => c.id === Number(id));
  return cat ? cat.nombre : '—';
}

getNombreProveedorById(id: number | null | undefined): string {
  const prov = this.proveedores.find(p => p.id === Number(id));
  return prov ? prov.nombre : '—';
}


  editarProducto(id: number): void {
    this.router.navigate(['/empresa/producto-create'], { queryParams: { id } });
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
