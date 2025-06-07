import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/core/services/token.service';
import { SessionService } from 'src/app/core/services/session.service';
import { UserService } from 'src/app/core/services/user.service';
import { Subscription } from 'rxjs';
import Swal from 'sweetalert2';

import { SolicitudMovimientoService } from 'src/app/modules/empresa-admin/services/solicitud-movimiento-service.service';
import { SolicitudPersonalizadaService } from 'src/app/modules/empresa-admin/services/solicitud-personalizada-service.service';

import { SolicitudMovimientoEmpleadoService } from 'src/app/modules/empleado/services/solicitud-movimiento-Empleado.service';
import { SolicitudPersonalizadaEmpleadoService } from 'src/app/modules/empleado/services/solicitud-personalizada-Empleado.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {
  dropdownVisible = false;
  timeoutRef: any;
  sesionIniciada = false;
  menuAbierto = false;
  mostrarModalNotificacionesAdmin = false;
  mostrarModalNotificacionesEmpleado = false;

  esAdmin = false;
  esAdminEmpresa = false;
  esEmpleado = false;
  esUsuario = false;

  nombreUsuario: string = 'Cuenta';
  notificacionesAdmin: number = 0;
  notificacionesEmpleado: number = 0;

  private sessionSub!: Subscription;

  constructor(
    private tokenService: TokenService,
    private sessionService: SessionService,
    private userService: UserService,
    private router: Router,
    private solicitudService: SolicitudMovimientoService,
    private solicitudPersonalizadaService: SolicitudPersonalizadaService,
    private solicitudMovimientoServiceEmpleado: SolicitudMovimientoEmpleadoService,
    private solicitudPersonalizadaServiceEmpleado: SolicitudPersonalizadaEmpleadoService
  ) { }

  ngOnInit(): void {
    this.actualizarEstado();

    this.sessionSub = this.sessionService.cambios$.subscribe(() => {
      this.actualizarEstado();
    });

    if (this.tokenService.isLogged()) {
      if (this.tokenService.hasRole('ROLE_ADMIN_EMPRESA')) {
        setTimeout(() => this.cargarNotificacionesAdmin(), 150);
      }

      if (this.tokenService.hasRole('ROLE_EMPLEADO')) {
        setTimeout(() => this.cargarNotificacionesEmpleado(), 150);
      }
    }
  }

  cargarNotificacionesAdmin(): void {
    this.solicitudService.getSolicitudesNoLeidas().subscribe({
      next: (movimientos) => {
        const totalMovs = movimientos.length;

        this.solicitudPersonalizadaService.getSolicitudesNoLeidas().subscribe({
          next: (personalizadas) => {
            this.notificacionesAdmin = totalMovs + personalizadas.length;
          },
          error: () => {
            this.notificacionesAdmin = totalMovs;
          }
        });
      },
      error: () => {
        this.notificacionesAdmin = 0;
      }
    });
  }

  cargarNotificacionesEmpleado(): void {
    const userId = this.tokenService.getUserId();
    if (!userId) return;

    this.solicitudMovimientoServiceEmpleado.getNoLeidasEmpleado(userId).subscribe({
      next: (movimientos) => {
        const totalMovs = movimientos.length;

        this.solicitudPersonalizadaServiceEmpleado.getNoLeidasEmpleado(userId).subscribe({
          next: (personalizadas) => {
            this.notificacionesEmpleado = totalMovs + personalizadas.length;
          },
          error: () => {
            this.notificacionesEmpleado = totalMovs;
          }
        });
      },
      error: () => {
        this.notificacionesEmpleado = 0;
      }
    });
  }

  actualizarEstado(): void {
    this.sesionIniciada = this.tokenService.isLogged();
    const usuario = this.tokenService.getUser();
    this.nombreUsuario = usuario?.username || 'Cuenta';

    this.esAdmin = this.tokenService.hasRole('ROLE_ADMIN');
    this.esAdminEmpresa = this.tokenService.hasRole('ROLE_ADMIN_EMPRESA');
    this.esEmpleado = this.tokenService.hasRole('ROLE_EMPLEADO');
    this.esUsuario = this.tokenService.hasRole('ROLE_USER');
    this.dropdownVisible = false;
  }

  ngOnDestroy(): void {
    this.sessionSub?.unsubscribe();
  }

  mostrarMenu(): void {
    clearTimeout(this.timeoutRef);
    this.dropdownVisible = true;
  }

  ocultarMenu(): void {
    this.timeoutRef = setTimeout(() => {
      this.dropdownVisible = false;
    }, 150);
  }

  cerrarSesion(): void {
    this.sessionService.cerrarSesion();
    setTimeout(() => this.router.navigate(['/auth/login']), 50);
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
              this.router.navigate(['/auth/login']);
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
