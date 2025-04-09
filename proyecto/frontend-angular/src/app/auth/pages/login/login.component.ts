import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AutenticationService } from '../../service/autentication.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  credenciales = { login: '', pass: '' }; // Modelo para el login
  errorInicioSesion: boolean = false;

  constructor(
    private router: Router,
    private autenticacionService: AutenticationService
  ) {}

  /**
   * Maneja el inicio de sesión.
   */
  login(): void {
    if (!this.credenciales.login || !this.credenciales.pass) {
      this.errorInicioSesion = true;
      return;
    }

    this.autenticacionService.iniciarSesion(this.credenciales.login, this.credenciales.pass).subscribe({
      next: () => {
        this.errorInicioSesion = false;
        this.router.navigate(['/dashboard']); // Redirige al dashboard
      },
      error: () => {
        this.errorInicioSesion = true; // Muestra un mensaje de error
      }
    });
  }
}