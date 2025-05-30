import { Component, Input, Output, EventEmitter } from '@angular/core';
import { Producto } from 'src/app/core/interfaces/producto';
import { NuevaSolicitudMovimiento } from 'src/app/core/interfaces/NuevaSolicitudMovimiento';
import { SolicitudMovimientoService } from '../../../services/solicitud-movimiento.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-formulario-solicitud',
  templateUrl: './formulario-solicitud.component.html',
  styleUrls: ['./formulario-solicitud.component.css']
})
export class FormularioSolicitudComponent {
  @Input() producto!: Producto;
  @Output() cerrarModal = new EventEmitter<void>();

  cantidadSolicitada: number = 1;
  motivo: string = '';

  constructor(private solicitudService: SolicitudMovimientoService) {}

  enviar(): void {
    const dto: NuevaSolicitudMovimiento = {
      productoId: this.producto.id!,
      cantidadSolicitada: this.cantidadSolicitada,
      motivo: this.motivo
    };

    this.solicitudService.crear(dto).subscribe({
      next: () => {
        Swal.fire('Solicitud enviada', 'La solicitud se ha registrado correctamente.', 'success');
        console.log('Solicitud enviada:', dto);
        this.cerrarModal.emit();
      },
      error: () => {
        Swal.fire('Error', 'No se pudo registrar la solicitud.', 'error');
      }
    });
  }

  cerrar(): void {
    this.cerrarModal.emit();
  }
}
