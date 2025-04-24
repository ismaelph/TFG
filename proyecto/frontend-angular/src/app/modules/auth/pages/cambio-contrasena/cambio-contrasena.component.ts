import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RestablecerContraseñaService } from '../../services/restablecer-contraseña.service.service';

@Component({
  selector: 'app-cambio-contrasena',
  templateUrl: './cambio-contrasena.component.html',
})
export class CambioContrasenaComponent implements OnInit {
  
  email: string = '';
  token: string = '';
  password: string = '';
  passwordConfirmation: string = '';
  mensaje: string = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private restablecerContraseñaService: RestablecerContraseñaService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.email = params['email'];
      this.token = params['token'];
    
      this.restablecerContraseñaService.validarToken(this.email, this.token).subscribe(
        response => {
          console.log('Token válido:', response);
        },
        error => {
          console.log('Error al validar token:', error);
          this.mensaje = 'El token es inválido o ha expirado.';
        }
      );
    });
  }
  

  // Método para manejar el cambio de contraseña
  cambiarContrasena() {
    if (!this.password || !this.passwordConfirmation) {
      this.mensaje = 'Por favor, ingrese la nueva contraseña.';
      return;
    }

    this.restablecerContraseñaService.resetPassword(
      this.email, 
      this.token, 
      this.password, 
      this.passwordConfirmation
    ).subscribe(
      response => {
        this.mensaje = 'Contraseña restablecida con éxito. Redirigiendo...';
        setTimeout(() => this.router.navigate(['/auth/login']), 3000); // Redirigir al login
      },
      error => {
        this.mensaje = 'Hubo un error al cambiar la contraseña. Verifique los datos e inténtelo de nuevo.';
      }
    );
  }
}
