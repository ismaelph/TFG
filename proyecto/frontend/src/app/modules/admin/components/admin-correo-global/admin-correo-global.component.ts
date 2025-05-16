import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-admin-correo-global',
  templateUrl: './admin-correo-global.component.html',
  styleUrls: ['./admin-correo-global.component.css']
})
export class AdminCorreoGlobalComponent {
  asunto: string = '';
  mensaje: string = '';
  cargando: boolean = false;

  constructor(private adminService: AdminService) {}

  enviar(): void {
    if (!this.asunto.trim() || !this.mensaje.trim()) {
      Swal.fire('Error', 'Debes completar todos los campos.', 'error');
      return;
    }

    this.cargando = true;
    this.adminService.enviarCorreoGlobal(this.asunto, this.mensaje).subscribe({
      next: () => {
        Swal.fire('Enviado', 'Correo enviado correctamente a todos los usuarios.', 'success');
        this.asunto = '';
        this.mensaje = '';
        this.cargando = false;
      },
      error: () => {
        Swal.fire('Error', 'No se pudo enviar el correo.', 'error');
        this.cargando = false;
      }
    });
  }
}
