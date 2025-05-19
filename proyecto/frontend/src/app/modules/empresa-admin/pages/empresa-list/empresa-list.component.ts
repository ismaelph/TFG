import { Component, OnInit } from '@angular/core';
import { EmpresaService } from '../../services/empresa.service';
import { Empresa } from '../../../../core/interfaces/empresa';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { SessionService } from 'src/app/core/services/session.service';

@Component({
  selector: 'app-empresa-list',
  templateUrl: './empresa-list.component.html',
  styleUrls: ['./empresa-list.component.css']
})
export class EmpresaListComponent implements OnInit {
  empresa!: Empresa;
  cargando = true;
  error: string | null = null;

  constructor(
    private empresaService: EmpresaService,
    private router: Router,
    private route: ActivatedRoute,
    private sessionService: SessionService // Cambia esto por el servicio correcto
  ) { }

  ngOnInit(): void {
    this.empresaService.getMiEmpresa().subscribe({
      next: (res) => {
        this.empresa = res;
        this.cargando = false;
      },
      error: (err) => {
        if (err.status === 204 || err.status === 404) {
          // El usuario no tiene empresa, es válido
          this.empresa = undefined as any;
        } else {
          this.error = 'No se pudo cargar tu empresa.';
        }
        this.cargando = false;
      }

    });
  }

  editar(): void {
    const id = this.empresa?.id;
    if (id && typeof id === 'number') {
      this.router.navigate(['../form', id], { relativeTo: this.route });
    } else {
      console.error('ID de empresa inválido:', id);
    }
  }

  borrar(): void {
  Swal.fire({
    title: '¿Estás seguro?',
    text: 'Esta acción eliminará permanentemente la empresa.',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonText: 'Sí, eliminar',
    cancelButtonText: 'Cancelar',
    confirmButtonColor: '#d33',
    cancelButtonColor: '#3085d6'
  }).then(result => {
    if (result.isConfirmed) {
      const id = this.empresa?.id;
      if (id && typeof id === 'number') {
        this.empresaService.eliminarEmpresa(id).subscribe({
          next: () => {
            // ✅ Cerramos sesión SOLO después del delete exitoso
            this.sessionService.cerrarSesion();

            // ✅ Redirigimos al login
            this.router.navigate(['/auth/login']);
          },
          error: (err) => {
            console.error('Error al eliminar empresa:', err);
            Swal.fire('Error', 'No se pudo eliminar la empresa.', 'error');
          }
        });
      }
    }
  });
}


}
