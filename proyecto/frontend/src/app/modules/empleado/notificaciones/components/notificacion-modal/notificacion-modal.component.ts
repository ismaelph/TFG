import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { SolicitudMovimiento } from 'src/app/core/interfaces/SolicitudMovimiento';
import { SolicitudPersonalizada } from 'src/app/core/interfaces/SolicitudPersonalizada';
import { TokenService } from 'src/app/core/services/token.service';
import { SolicitudMovimientoService } from '../../../services/solicitud-movimiento.service';
import { SolicitudPersonalizadaService } from '../../../services/solicitud-personalizada.service';

@Component({
  selector: 'app-notificacion-modal-empleado',
  templateUrl: './notificacion-modal.component.html',
  styleUrls: ['./notificacion-modal.component.css']
})
export class NotificacionModalEmpleadoComponent implements OnInit {
  @Output() cerrar = new EventEmitter<void>();

  solicitudes: (SolicitudMovimiento | SolicitudPersonalizada)[] = [];

  constructor(
    private movimientoService: SolicitudMovimientoService,
    private personalizadaService: SolicitudPersonalizadaService,
    private tokenService: TokenService
  ) { }

  ngOnInit(): void {
    this.cargarSolicitudes();
  }

  cargarSolicitudes(): void {
    const userId = this.tokenService.getUserId();

    if (!userId) {
      console.warn('❌ No se pudo obtener el ID del usuario.');
      return;
    }

    this.movimientoService.getPorUsuario(userId).subscribe(movs => {
      this.personalizadaService.obtenerMisSolicitudes(userId).subscribe(pers => {
        this.solicitudes = [
          ...movs.filter(s => s.estado !== 'PENDIENTE'),
          ...pers.filter(s => s.estado !== 'PENDIENTE')
        ];
        console.log('📨 Solicitudes resueltas del usuario:', this.solicitudes);
      });
    });
  }

  getNombreProducto(noti: SolicitudMovimiento | SolicitudPersonalizada): string {
    return 'nombreProductoSugerido' in noti
      ? noti.nombreProductoSugerido || 'Sugerencia sin nombre'
      : noti.productoNombre || 'Producto desconocido';
  }


  getEstado(noti: SolicitudMovimiento | SolicitudPersonalizada): string {
    return noti.estado ?? '—';
  }

  cerrarModal(): void {
    this.cerrar.emit();
  }
}
