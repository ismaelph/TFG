import { Component, EventEmitter, OnInit, Output, OnDestroy } from '@angular/core';
import { SolicitudMovimientoService } from '../../services/solicitud-movimiento-service.service';
import { SolicitudPersonalizadaService } from '../../services/solicitud-personalizada-service.service';
import { Subscription, interval } from 'rxjs';

@Component({
  selector: 'app-notificacion-badge',
  templateUrl: './notificacion-badge.component.html',
  styleUrls: ['./notificacion-badge.component.css']
})
export class NotificacionBadgeComponent implements OnInit, OnDestroy {
  @Output() clickBadge = new EventEmitter<void>();
  totalNotificaciones = 0;

  private actualizadorSub?: Subscription;

  constructor(
    private movService: SolicitudMovimientoService,
    private perService: SolicitudPersonalizadaService
  ) {}

  ngOnInit(): void {
    // Ejecuta cada 5 segundos
    this.actualizadorSub = interval(5000).subscribe(() => this.actualizarNotificaciones());
    // Carga inicial inmediata
    this.actualizarNotificaciones();
  }

  ngOnDestroy(): void {
    this.actualizadorSub?.unsubscribe();
  }

  actualizarNotificaciones(): void {
    let normales = 0;
    let personalizadas = 0;

    this.movService.getSolicitudesNoLeidas().subscribe({
      next: (data) => {
        normales = data.length;
        this.totalNotificaciones = normales + personalizadas;
      },
      error: () => {
        console.warn('❌ Error al cargar solicitudes normales');
      }
    });

    this.perService.getSolicitudesNoLeidas().subscribe({
      next: (data) => {
        personalizadas = data.length;
        this.totalNotificaciones = normales + personalizadas;
      },
      error: () => {
        console.warn('❌ Error al cargar solicitudes personalizadas');
      }
    });
  }

  abrir(): void {
    this.clickBadge.emit();
  }
}
