import { Component, OnInit } from '@angular/core';
import { EmpresaService } from '../../services/empresa.service';
import { Empresa } from '../../../../core/interfaces/empresa';
import { ActivatedRoute, Router } from '@angular/router';
import Swal from 'sweetalert2';
import { SessionService } from 'src/app/core/services/session.service';
import { TokenService } from 'src/app/core/services/token.service';

@Component({
  selector: 'app-empresa-list',
  templateUrl: './empresa-list.component.html',
  styleUrls: ['./empresa-list.component.css']
})
export class EmpresaListComponent implements OnInit {
  empresa!: Empresa;
  cargando = true;
  error: string | null = null;
  esAdminEmpresa = false;

  constructor(
    private empresaService: EmpresaService,
    private router: Router,
    private route: ActivatedRoute,
    private sessionService: SessionService,
    private tokenService: TokenService
  ) { }

  ngOnInit(): void {
    this.esAdminEmpresa = this.tokenService.hasRole('ROLE_ADMIN_EMPRESA');
    console.log('🔑 Rol admin empresa:', this.esAdminEmpresa);

    this.empresaService.getMiEmpresa().subscribe({
      next: (res) => {
        this.empresa = res;
        this.cargando = false;
        console.log('✅ Empresa cargada:', this.empresa);
      },
      error: (err) => {
        this.cargando = false;
        console.error('❌ Error al obtener empresa:', err);

        if (err.status === 204 || err.status === 404) {
          this.empresa = undefined as any;
          console.warn('⚠️ El usuario no está vinculado a ninguna empresa.');
        } else {
          this.error = 'No se pudo cargar tu empresa.';
        }
      }
    });
  }

  editar(): void {
    const id = this.empresa?.id;
    if (id && typeof id === 'number') {
      console.log('✏️ Editando empresa con ID:', id);
      this.router.navigate(['../form', id], { relativeTo: this.route });
    } else {
      console.error('❌ ID de empresa inválido para editar:', id);
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
          console.log('🗑️ Eliminando empresa con ID:', id);

          this.empresaService.eliminarEmpresa(id).subscribe({
            next: () => {
              console.log('✅ Empresa eliminada con éxito.');
              this.sessionService.cerrarSesion();
              this.router.navigate(['/auth/login']);
            },
            error: (err) => {
              console.error('❌ Error al eliminar empresa:', err);
              Swal.fire('Error', 'No se pudo eliminar la empresa.', 'error');
            }
          });
        } else {
          console.error('❌ ID de empresa inválido para eliminación:', id);
        }
      }
    });
  }
}
