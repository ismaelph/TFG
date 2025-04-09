import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AutenticationService } from '../../service/autentication.service';
import { RegisterRequest } from '../../interfaces/autentication';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  registro: RegisterRequest = { username: '', email: '', password: '', roles: [] }; // Modelo para el registro
  errorRegistro: boolean = false;
  mensajeRegistroExitoso: boolean = false;

  constructor(
    private autenticacionService: AutenticationService,
    private router: Router
  ) {}

  /**
   * Maneja el registro de un nuevo usuario.
   */
  registrar(): void {
    if (!this.registro.username || !this.registro.email || !this.registro.password) {
      this.errorRegistro = true;
      return;
    }

    this.autenticacionService.registrarUsuario(this.registro).subscribe({
      next: () => {
        this.mensajeRegistroExitoso = true; // Muestra un mensaje de éxito
        this.errorRegistro = false;
        this.registro = { username: '', email: '', password: '', roles: [] }; // Limpia el formulario
        setTimeout(() => this.router.navigate(['/login']), 3000); // Redirige al login tras 3 segundos
      },
      error: () => {
        this.errorRegistro = true; // Muestra un mensaje de error
        this.mensajeRegistroExitoso = false;
      }
    });
  }
}