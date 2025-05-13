import { Component, OnInit } from '@angular/core';
import { CategoriaService } from '../../services/categoria.service';
import { Categoria } from 'src/app/core/interfaces/categoria';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-categoria-list',
  templateUrl: './categoria-list.component.html',
  styleUrls: ['./categoria-list.component.css']
})
export class CategoriaListComponent implements OnInit {
  categorias: Categoria[] = [];
  filtradas: Categoria[] = [];
  cargando = true;
  error: string | null = null;

  searchTerm: string = '';
  ordenAscendente: boolean = true;

  constructor(
    private categoriaService: CategoriaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.obtenerCategorias();
  }

  obtenerCategorias(): void {
    this.cargando = true;
    this.categoriaService.getCategorias().subscribe({
      next: (data) => {
        this.categorias = data;
        this.filtrarYOrdenar();
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar las categorías.';
        this.cargando = false;
      }
    });
  }

  filtrarYOrdenar(): void {
    const termino = this.searchTerm.toLowerCase();
    this.filtradas = this.categorias
      .filter(c => c.nombre.toLowerCase().includes(termino))
      .sort((a, b) => {
        const comp = a.nombre.localeCompare(b.nombre);
        return this.ordenAscendente ? comp : -comp;
      });
  }

  actualizarFiltro(): void {
    this.filtrarYOrdenar();
  }

  cambiarOrden(): void {
    this.ordenAscendente = !this.ordenAscendente;
    this.filtrarYOrdenar();
  }

  editar(cat: Categoria): void {
    this.router.navigate(['/empresa/categoria-edit', cat.id]);
  }

  eliminar(id: number): void {
    Swal.fire({
      title: '¿Eliminar categoría?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#e3342f',
      cancelButtonColor: '#6B7280'
    }).then(result => {
      if (result.isConfirmed) {
        this.categoriaService.eliminarCategoria(id).subscribe({
          next: () => {
            this.categorias = this.categorias.filter(c => c.id !== id);
            this.filtrarYOrdenar();
            Swal.fire('Eliminada', 'Categoría eliminada correctamente.', 'success');
          },
          error: () => {
            Swal.fire('Error', 'No se pudo eliminar la categoría.', 'error');
          }
        });
      }
    });
  }
}
