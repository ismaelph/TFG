import { Component, OnInit } from '@angular/core';
import { ProveedorService } from '../../services/proveedor.service';
import { Proveedor } from 'src/app/core/interfaces/proveedor';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-proveedor-list',
  templateUrl: './proveedor-list.component.html',
  styleUrls: ['./proveedor-list.component.css']
})
export class ProveedorListComponent implements OnInit {
  proveedores: Proveedor[] = [];
  filtrados: Proveedor[] = [];
  cargando = true;
  error: string | null = null;

  proveedorSeleccionado: Proveedor | null = null;

  searchTerm: string = '';
  ordenAscendente: boolean = true;

  constructor(
    private proveedorService: ProveedorService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.obtenerProveedores();
  }

  obtenerProveedores(): void {
    this.cargando = true;
    this.proveedorService.getProveedores().subscribe({
      next: (data) => {
        this.proveedores = data;
        this.filtrarYOrdenar();
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar los proveedores.';
        this.cargando = false;
      }
    });
  }

  filtrarYOrdenar(): void {
    const termino = this.searchTerm.toLowerCase();
    this.filtrados = this.proveedores
      .filter(p =>
        p.nombre.toLowerCase().includes(termino)
      )

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

  abrirModal(proveedor: Proveedor): void {
    this.proveedorSeleccionado = proveedor;
  }

  cerrarModal(): void {
    this.proveedorSeleccionado = null;
  }

  editar(prov: Proveedor): void {
    this.router.navigate(['/empresa/proveedor-edit', prov.id]);
  }

  eliminar(id: number): void {
    Swal.fire({
      title: '¿Eliminar proveedor?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#e3342f',
      cancelButtonColor: '#6B7280'
    }).then(result => {
      if (result.isConfirmed) {
        this.proveedorService.eliminarProveedor(id).subscribe({
          next: () => {
            this.proveedores = this.proveedores.filter(p => p.id !== id);
            this.filtrarYOrdenar();
            Swal.fire('Eliminado', 'Proveedor eliminado correctamente.', 'success');
          },
          error: () => {
            Swal.fire('Error', 'No se pudo eliminar el proveedor.', 'error');
          }
        });
      }
    });
  }
}
