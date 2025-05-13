import { Component, OnInit, HostListener } from '@angular/core';
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
  cargando = true;
  error: string | null = null;

  proveedorSeleccionado: Proveedor | null = null;

  constructor(
    private proveedorService: ProveedorService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.obtenerProveedores();
  }

  obtenerProveedores(): void {
    this.cargando = true;
    this.proveedorService.getProveedores().subscribe({
      next: (data) => {
        this.proveedores = data;
        this.cargando = false;
      },
      error: () => {
        this.error = 'No se pudieron cargar los proveedores.';
        this.cargando = false;
      }
    });
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
