import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { TokenService } from 'src/app/core/services/token.service';
import { SessionService } from 'src/app/core/services/session.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit, OnDestroy {
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
    private router: Router
  ) { }

  ngOnInit() {
    this.sesionIniciada = this.tokenService.isLogged();

    this.sessionSub = this.sessionService.isSesionIniciada().subscribe((activa) => {
      this.sesionIniciada = activa;

      if (activa) {
        const usuario = this.tokenService.getUser();
        this.nombreUsuario = usuario?.username || 'Cuenta'; // ✅ Se actualiza al iniciar sesión

        this.esAdmin = this.tokenService.hasRole('ROLE_ADMIN');
        this.esAdminEmpresa = this.tokenService.hasRole('ROLE_ADMIN_EMPRESA');
        this.esEmpleado = this.tokenService.hasRole('ROLE_EMPLEADO');
        this.esUsuario = this.tokenService.hasRole('ROLE_USER');

        this.dropdownVisible = false;
      } else {
        this.nombreUsuario = 'Cuenta';
        this.dropdownVisible = false;
        this.esAdmin = false;
        this.esAdminEmpresa = false;
        this.esEmpleado = false;
        this.esUsuario = false;
      }
    });
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
}
