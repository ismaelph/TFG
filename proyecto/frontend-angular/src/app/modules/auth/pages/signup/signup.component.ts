import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AutenticacionService } from '../../services/autenticacion.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  credenciales = {
    username: '',
    email: '',
    password: '',
    repetirContrasena: '' // Campo para repetir la contraseña
  };

  mostrarPassword: boolean = false;
  errorRegistro: boolean = false;
  mensajeError: string = '';

  constructor(
    private autenticacionService: AutenticacionService,
    private router: Router
  ) {}

  /**
   * Alterna la visibilidad de la contraseña
   */
  contrasenaVisible(): void {
    this.mostrarPassword = !this.mostrarPassword;
  }

  /**
   * Maneja el registro del usuario
   */
  signup(): void {
    // Validar que las contraseñas coincidan
    if (this.credenciales.password !== this.credenciales.repetirContrasena) {
      this.errorRegistro = true;
      this.mensajeError = 'Las contraseñas no coinciden.';
      return;
    }

    // Llamar al servicio de autenticación para registrar al usuario
    this.autenticacionService.registrarUsuario(this.credenciales).subscribe({
      next: () => {
        // Redirigir al login después de un registro exitoso
        this.router.navigate(['/auth/login']);
      },
      error: (error) => {
        // Manejar errores de registro
        this.errorRegistro = true;
        this.mensajeError = error.error?.message || 'Error al registrarse. Inténtalo de nuevo.';
      }
    });
  }
}