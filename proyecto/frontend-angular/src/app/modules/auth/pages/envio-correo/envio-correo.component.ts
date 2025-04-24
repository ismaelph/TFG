import { Component } from '@angular/core';
import { RestablecerContraseñaService } from '../../services/restablecer-contraseña.service.service';

@Component({
  selector: 'app-envio-correo',
  templateUrl: './envio-correo.component.html',
})
export class EnvioCorreoComponent {
  email: string = '';
  mensaje: string = '';
  error: string = '';

  constructor(private restablecerContraseñaService: RestablecerContraseñaService) {}

  solicitarRestablecimiento() {
    this.mensaje = ''; // Reiniciar mensaje
    this.error = '';

    this.restablecerContraseñaService.resetPasswordRequest(this.email).subscribe({
      next: (response) => {
        this.mensaje = 'Se ha enviado un enlace de restablecimiento a tu correo.';
      },
      error: (err) => {
        this.error = err.error.message || 'Hubo un error al procesar la solicitud.';
      }
    });
  }
}
