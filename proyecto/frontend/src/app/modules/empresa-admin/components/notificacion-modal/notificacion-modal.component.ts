import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { SolicitudMovimiento } from 'src/app/core/interfaces/SolicitudMovimiento';
import { SolicitudPersonalizada } from 'src/app/core/interfaces/SolicitudPersonalizada';
import { SolicitudMovimientoService } from '../../services/solicitud-movimiento-service.service';
import { SolicitudPersonalizadaService } from '../../services/solicitud-personalizada-service.service';

@Component({
  selector: 'app-notificacion-modal',
  templateUrl: './notificacion-modal.component.html',
  styleUrls: ['./notificacion-modal.component.css']
})
export class NotificacionModalComponent implements OnInit {
  @Output() cerrar = new EventEmitter<void>();

  solicitudes: (SolicitudMovimiento | SolicitudPersonalizada)[] = [];

  constructor(
    private movimientoService: SolicitudMovimientoService,
    private personalizadaService: SolicitudPersonalizadaService
  ) {}

  ngOnInit(): void {
    this.cargarSolicitudes();
  }

  cargarSolicitudes(): void {
    this.movimientoService.getSolicitudesNoLeidas().subscribe(movs => {
      this.personalizadaService.getSolicitudesNoLeidas().subscribe(pers => {
        this.solicitudes = [
          ...movs.filter(m => m.estado === 'PENDIENTE'),
          ...pers.filter(p => p.estado === 'PENDIENTE')
        ];
        console.log('🔔 Notificaciones cargadas:', this.solicitudes);
      });
    });
  }

  cerrarModal(): void {
    this.cerrar.emit();
  }

  vaciarNotificaciones(): void {
    this.solicitudes = [];
  }
  
  getNombreUsuario(noti: SolicitudMovimiento | SolicitudPersonalizada): string {
    return 'usuarioNombre' in noti
      ? noti.usuarioNombre || 'Usuario desconocido'
      : 'Usuario desconocido';
  }
}
