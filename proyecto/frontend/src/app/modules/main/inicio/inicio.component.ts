import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AutenticacionService } from 'src/app/modules/auth/services/autenticacion.service';

@Component({
  selector: 'app-inicio',
  templateUrl: './inicio.component.html',
  styleUrls: ['./inicio.component.css']
})
export class InicioComponent {
  constructor(
    private autenticacionService: AutenticacionService,
    private router: Router
  ) {}

  explorarPlataforma(): void {
    if (this.autenticacionService.isSesionIniciada()) {
      // Redirige a la página principal de la plataforma
      this.router.navigate(['/main/dashboard']);
    } else {
      // Redirige a la página de inicio de sesión
      this.router.navigate(['/auth/login']);
    }
  }
}