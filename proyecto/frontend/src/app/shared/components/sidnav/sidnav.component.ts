import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/core/services/token.service';
import { SessionService } from 'src/app/core/services/session.service';
import { UserService } from 'src/app/core/services/user.service';
import { Subscription } from 'rxjs';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-sidnav',
  templateUrl: './sidnav.component.html',
  styleUrls: ['./sidnav.component.css']
})
export class SidnavComponent implements OnInit, OnDestroy {

  dropdownVisible = false;
  timeoutRef: any;
  sesionIniciada = false;
  menuAbierto: boolean = false;

  esAdmin = false;
  esAdminEmpresa = false;
  esEmpleado = false;
  esUsuario = false;

  nombreUsuario: string = 'Cuenta';

  private sessionSub!: Subscription;

  constructor(
    private tokenService: TokenService,
    private sessionService: SessionService,
    private userService: UserService,
    public router: Router // <- CAMBIO AQUÍ
  ) { }

  ngOnInit() {
    this.actualizarEstado();

    // 🔄 Escucha cualquier cambio de sesión (incluye actualizar perfil)
    this.sessionSub = this.sessionService.cambios$.subscribe(() => {
      this.actualizarEstado();
    });
  }

  private actualizarEstado() {
    this.sesionIniciada = this.tokenService.isLogged();

    if (this.sesionIniciada) {
      const usuario = this.tokenService.getUser();
      this.nombreUsuario = usuario?.username || 'Cuenta';

      this.esAdmin = this.tokenService.hasRole('ROLE_ADMIN');
      this.esAdminEmpresa = this.tokenService.hasRole('ROLE_ADMIN_EMPRESA');
      this.esEmpleado = this.tokenService.hasRole('ROLE_EMPLEADO');
      this.esUsuario = this.tokenService.hasRole('ROLE_USER');
    } else {
      this.nombreUsuario = 'Cuenta';
      this.esAdmin = false;
      this.esAdminEmpresa = false;
      this.esEmpleado = false;
      this.esUsuario = false;
    }

    this.dropdownVisible = false;
  }

  ngOnDestroy() {
    if (this.sessionSub) {
      this.sessionSub.unsubscribe();
    }
  }

  mostrarMenu() {
    clearTimeout(this.timeoutRef);
    this.dropdownVisible = true;
  }

  ocultarMenu() {
    this.timeoutRef = setTimeout(() => {
      this.dropdownVisible = false;
    }, 150);
  }

  cerrarSesion() {
    this.sessionService.cerrarSesion();
    this.router.navigate(['/auth/login']);
  }

  confirmarAbandono(): void {
    Swal.fire({
      icon: 'warning',
      title: '¿Estás seguro?',
      text: 'Perderás el acceso a esta empresa y sus datos.',
      showCancelButton: true,
      confirmButtonText: 'Sí, abandonar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#e53e3e',
      cancelButtonColor: '#6b7280'
    }).then(result => {
      if (result.isConfirmed) {
        const userId = this.tokenService.getUserId();
        if (!userId) {
          Swal.fire('Error', 'No se pudo obtener tu sesión.', 'error');
          return;
        }

        this.userService.abandonarEmpresa(userId).subscribe({
          next: () => {
            Swal.fire('Listo', 'Has abandonado la empresa.', 'success').then(() => {
              this.tokenService.clearAll();
              window.location.href = '/login';
            });
          },
          error: () => {
            Swal.fire('Error', 'No se pudo abandonar la empresa.', 'error');
          }
        });
      }
    });
  }
}
