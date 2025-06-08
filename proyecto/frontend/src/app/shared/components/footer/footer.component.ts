import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { TokenService } from 'src/app/core/services/token.service';
import { SessionService } from 'src/app/core/services/session.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit, OnDestroy {

  esAdmin = false;
  esAdminEmpresa = false;
  esEmpleado = false;
  esUsuario = false;
  sesionIniciada = false;

  sessionSub?: Subscription;

  constructor(
    private tokenService: TokenService,
    private sessionService: SessionService
  ) {}

  ngOnInit(): void {
    this.actualizarEstado();
    this.sessionSub = this.sessionService.cambios$.subscribe(() => {
      this.actualizarEstado();
    });
  }

  ngOnDestroy(): void {
    this.sessionSub?.unsubscribe();
  }

  actualizarEstado(): void {
    this.sesionIniciada = this.tokenService.isLogged();

    this.esAdmin = this.tokenService.hasRole('ROLE_ADMIN');
    this.esAdminEmpresa = this.tokenService.hasRole('ROLE_ADMIN_EMPRESA');
    this.esEmpleado = this.tokenService.hasRole('ROLE_EMPLEADO');
    this.esUsuario = this.tokenService.hasRole('ROLE_USER');
  }

  get mostrarUnirseEmpresa(): boolean {
    return this.sesionIniciada && this.esUsuario;
  }

  get mostrarLogin(): boolean {
    return !this.sesionIniciada;
  }
}
