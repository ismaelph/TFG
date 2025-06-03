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
  paginaActual = 1;
  elementosPorPagina = 6;

  constructor(
    private movimientoService: SolicitudMovimientoService,
    private personalizadaService: SolicitudPersonalizadaService
  ) { }

  ngOnInit(): void {
    this.cargarSolicitudes();
  }

  cargarSolicitudes(): void {
    this.movimientoService.getSolicitudesNoLeidas().subscribe(movs => {
      this.personalizadaService.getSolicitudesNoLeidas().subscribe(pers => {
        this.solicitudes = [...movs, ...pers];
        console.log('🔔 Notificaciones cargadas:', this.solicitudes);
      });
    });
  }

  cerrarModal(): void {
    this.cerrar.emit(); // ← este método sí cierra el modal desde el padre (navbar)
  }

  get solicitudesPaginadas(): any[] {
    const inicio = (this.paginaActual - 1) * this.elementosPorPagina;
    return this.solicitudes.slice(inicio, inicio + this.elementosPorPagina);
  }

  cambiarPagina(pagina: number): void {
    this.paginaActual = pagina;
  }

  aceptar(noti: any): void {
    console.log('✅ Aceptar:', noti);
    // aquí iría la lógica de aprobación usando el servicio adecuado
  }

  rechazar(noti: any): void {
    console.log('❌ Rechazar:', noti);
    // aquí iría la lógica de rechazo usando el servicio adecuado
  }
}
