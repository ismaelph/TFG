import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { SolicitudMovimientoService } from '../../services/solicitud-movimiento-service.service';
import { SolicitudPersonalizadaService } from '../../services/solicitud-personalizada-service.service';

@Component({
  selector: 'app-notificacion-badge',
  templateUrl: './notificacion-badge.component.html',
  styleUrls: ['./notificacion-badge.component.css']
})
export class NotificacionBadgeComponent implements OnInit {
  @Output() clickBadge = new EventEmitter<void>();
  totalNotificaciones = 0;

  constructor(
    private movService: SolicitudMovimientoService,
    private perService: SolicitudPersonalizadaService
  ) {}

  ngOnInit(): void {
    this.actualizarNotificaciones();
  }

  actualizarNotificaciones(): void {
    let normales = 0;
    let personalizadas = 0;

    this.movService.getSolicitudesNoLeidas().subscribe({
      next: (data) => {
        normales = data.length;
        this.totalNotificaciones = normales + personalizadas;
      }
    });

    this.perService.getSolicitudesNoLeidas().subscribe({
      next: (data) => {
        personalizadas = data.length;
        this.totalNotificaciones = normales + personalizadas;
      }
    });
  }

  abrir(): void {
    this.clickBadge.emit();
  }
}
