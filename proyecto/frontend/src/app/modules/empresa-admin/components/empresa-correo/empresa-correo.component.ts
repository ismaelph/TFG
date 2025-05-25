import { Component, Output, EventEmitter } from '@angular/core';
import { EmpresaService } from '../../services/empresa.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-empresa-correo',
  templateUrl: './empresa-correo.component.html',
  styleUrls: ['./empresa-correo.component.css']
})
export class EmpresaCorreoComponent {

  asunto: string = '';
  mensaje: string = '';
  cargando: boolean = false;

  constructor(private empresaService: EmpresaService) { }

  enviar(): void {
    if (!this.asunto.trim() || !this.mensaje.trim()) {
      Swal.fire('Error', 'Debes completar todos los campos.', 'error');
      return;
    }

    this.cargando = true;
    this.empresaService.enviarCorreoEmpleados(this.asunto, this.mensaje).subscribe({
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


  @Output() cerrar = new EventEmitter<void>();

  cancelar(): void {
    this.cerrar.emit();
  }

}
