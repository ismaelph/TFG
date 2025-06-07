import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { SolicitudMovimiento } from 'src/app/core/interfaces/SolicitudMovimiento';
import { SolicitudPersonalizada } from 'src/app/core/interfaces/SolicitudPersonalizada';
import { TokenService } from 'src/app/core/services/token.service';
import { SolicitudMovimientoEmpleadoService } from '../../../services/solicitud-movimiento-Empleado.service';
import { SolicitudPersonalizadaEmpleadoService } from '../../../services/solicitud-personalizada-Empleado.service';

@Component({
  selector: 'app-notificacion-modal-empleado',
  templateUrl: './notificacion-modal.component.html',
  styleUrls: ['./notificacion-modal.component.css']
})
export class NotificacionModalEmpleadoComponent implements OnInit {
  @Output() cerrar = new EventEmitter<void>();
  @Output() cambioNotificaciones = new EventEmitter<number>();

  solicitudes: (SolicitudMovimiento | SolicitudPersonalizada)[] = [];

  constructor(
    private movimientoService: SolicitudMovimientoEmpleadoService,
    private personalizadaService: SolicitudPersonalizadaEmpleadoService,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    this.cargarSolicitudes();
  }

  cargarSolicitudes(): void {
    const userId = this.tokenService.getUserId();

    if (!userId) {
      console.warn('❌ No se pudo obtener el ID del usuario.');
      return;
    }

    this.movimientoService.getNoLeidasEmpleado(userId).subscribe(movs => {
      this.personalizadaService.getNoLeidasEmpleado(userId).subscribe(pers => {
        this.solicitudes = [
          ...movs.filter(s => s.estado !== 'PENDIENTE'),
          ...pers.filter(s => s.estado !== 'PENDIENTE')
        ];

        console.log('📨 Solicitudes resueltas del usuario (NO LEÍDAS):', this.solicitudes);
        this.cambioNotificaciones.emit(this.solicitudes.length);

        // Marcar como leídas para el empleado
        this.solicitudes.forEach(solicitud => {
          if (!solicitud.id) return;

          if ('productoNombre' in solicitud) {
            this.movimientoService.marcarLeidaEmpleado(solicitud.id).subscribe(() => {
              console.log('✅ Movimiento marcado como leído por empleado:', solicitud.id);
            });
          } else {
            this.personalizadaService.marcarLeidaEmpleado(solicitud.id).subscribe(() => {
              console.log('✅ Personalizada marcada como leída por empleado:', solicitud.id);
            });
          }
        });
      });
    });
  }

  getNombreProducto(noti: SolicitudMovimiento | SolicitudPersonalizada): string {
    return 'nombreProductoSugerido' in noti
      ? noti.nombreProductoSugerido || 'Sugerencia sin nombre'
      : (noti as SolicitudMovimiento).productoNombre || 'Producto desconocido';
  }

  getEstado(noti: SolicitudMovimiento | SolicitudPersonalizada): string {
    return noti.estado ?? '—';
  }

  cerrarModal(): void {
    this.cerrar.emit();
  }
}
