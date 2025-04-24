import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AutenticacionService } from '../../services/autenticacion.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {

  credenciales = {
    login: '',
    pass: ''
  }

  errorInicioSesion: boolean = false;

  errorMensaje: string = '';

  constructor(
    private router: Router,
    private autenticacionService: AutenticacionService
  ) { }

  ngOnInit(): void {
  }

  login() {
    this.autenticacionService.iniciarSesion(this.credenciales.login, this.credenciales.pass)

      // Se suscribe al observable que retorna el método iniciarSesion del servicio de autenticación
      .subscribe({
        next: (autentificado: boolean) => {
          this.router.navigate(['/home/dashboard']);
        },

        complete: () => { },

        // En caso de error, se muestra el mensaje de éste en el html
        error: (error: any) => {
          this.errorInicioSesion = true;

          // Si el error dado es de tipo HttpErrorResponse, se almacena el mensaje de error para poder mostrarlo en el html
          if (error instanceof HttpErrorResponse) {
            this.errorMensaje = error.error?.message || 'Error en el inicio de sesión. Verifica tus credenciales.';
          } else {
            // Error no HTTP (de red, timeout, etc.)
            this.errorMensaje = 'No se pudo conectar con el servidor. Inténtalo más tarde.';
          }
        }
      });
  }
}
