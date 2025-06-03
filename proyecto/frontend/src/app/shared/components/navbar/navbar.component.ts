import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/core/services/token.service';
import { SessionService } from 'src/app/core/services/session.service';
import { UserService } from 'src/app/core/services/user.service';
import { Subscription } from 'rxjs';
import Swal from 'sweetalert2';

import { SolicitudMovimientoService } from 'src/app/modules/empresa-admin/services/solicitud-movimiento-service.service';
import { SolicitudPersonalizadaService } from 'src/app/modules/empresa-admin/services/solicitud-personalizada-service.service';

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
  mostrarModalNotificaciones = false;

  esAdmin = false;
  esAdminEmpresa = false;
  esEmpleado = false;
  esUsuario = false;

  nombreUsuario: string = 'Cuenta';
  notificaciones: number = 0;

  private sessionSub!: Subscription;

  constructor(
    private tokenService: TokenService,
    private sessionService: SessionService,
    private userService: UserService,
    private router: Router,
    private solicitudService: SolicitudMovimientoService,
    private solicitudPersonalizadaService: SolicitudPersonalizadaService
  ) {}

  ngOnInit(): void {
    console.log('🔄 Navbar inicializado');
    this.actualizarEstado();

    this.sessionSub = this.sessionService.cambios$.subscribe(() => {
      console.log('🟢 Sesión actualizada por cambios$');
      this.actualizarEstado();
    });

    if (this.tokenService.isLogged() && this.tokenService.hasRole('ROLE_ADMIN_EMPRESA')) {
      setTimeout(() => this.cargarNotificaciones(), 150);
    }
  }

  private cargarNotificaciones(): void {
    const token = this.tokenService.getToken();
    if (!token) {
      console.warn('⚠️ Token no disponible aún. Cancelando carga de notificaciones.');
      return;
    }

    let total = 0;

    this.solicitudService.getSolicitudesNoLeidas().subscribe({
      next: (movimientos) => {
        total += movimientos.length;

        this.solicitudPersonalizadaService.getSolicitudesNoLeidas().subscribe({
          next: (personalizadas) => {
            total += personalizadas.length;
            this.notificaciones = total;
            console.log('🔔 Total notificaciones:', this.notificaciones);
          },
          error: () => {
            console.warn('⚠️ Error cargando solicitudes personalizadas');
            this.notificaciones = total;
          }
        });
      },
      error: () => {
        console.warn('⚠️ Error cargando solicitudes normales');
        this.notificaciones = 0;
      }
    });
  }

  private actualizarEstado(): void {
    this.sesionIniciada = this.tokenService.isLogged();
    console.log('✅ Token:', this.tokenService.getToken());
    console.log('👤 Usuario:', this.tokenService.getUser());

    if (this.sesionIniciada) {
      const usuario = this.tokenService.getUser();
      this.nombreUsuario = usuario?.username || 'Cuenta';

      this.esAdmin = this.tokenService.hasRole('ROLE_ADMIN');
      this.esAdminEmpresa = this.tokenService.hasRole('ROLE_ADMIN_EMPRESA');
      this.esEmpleado = this.tokenService.hasRole('ROLE_EMPLEADO');
      this.esUsuario = this.tokenService.hasRole('ROLE_USER');

      console.log('🎯 Roles → Admin:', this.esAdmin, '| AdminEmpresa:', this.esAdminEmpresa, '| Empleado:', this.esEmpleado, '| User:', this.esUsuario);
    } else {
      console.warn('⛔ No hay sesión activa.');
      this.nombreUsuario = 'Cuenta';
      this.esAdmin = this.esAdminEmpresa = this.esEmpleado = this.esUsuario = false;
    }

    this.dropdownVisible = false;
  }

  ngOnDestroy(): void {
    if (this.sessionSub) {
      this.sessionSub.unsubscribe();
    }
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
