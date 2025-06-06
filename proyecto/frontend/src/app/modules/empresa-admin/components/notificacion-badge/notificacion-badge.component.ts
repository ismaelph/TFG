import { Component, EventEmitter, OnInit, Output, OnDestroy } from '@angular/core';
import { SolicitudMovimientoService } from '../../services/solicitud-movimiento-service.service';
import { SolicitudPersonalizadaService } from '../../services/solicitud-personalizada-service.service';
import { Subscription, interval, forkJoin } from 'rxjs';

@Component({
  selector: 'app-notificacion-badge',
  templateUrl: './notificacion-badge.component.html',
  styleUrls: ['./notificacion-badge.component.css']
})
export class NotificacionBadgeComponent implements OnInit, OnDestroy {
  @Output() clickBadge = new EventEmitter<void>();
  @Output() cambioNotificaciones = new EventEmitter<number>();

  totalNotificaciones = 0;
  private actualizadorSub?: Subscription;

  constructor(
    private movService: SolicitudMovimientoService,
    private perService: SolicitudPersonalizadaService
  ) {}

  ngOnInit(): void {
    this.actualizadorSub = interval(1000).subscribe(() => this.actualizarNotificaciones());
    this.actualizarNotificaciones(); // Carga inicial inmediata
  }

  ngOnDestroy(): void {
    this.actualizadorSub?.unsubscribe();
  }

  actualizarNotificaciones(): void {
    forkJoin({
      movs: this.movService.getSolicitudesNoLeidas(),
      pers: this.perService.getSolicitudesNoLeidas()
    }).subscribe({
      next: ({ movs, pers }) => {
        const normales = movs.filter(m => m.estado === 'PENDIENTE').length;
        const personalizadas = pers.filter(p => p.estado === 'PENDIENTE').length;
        this.totalNotificaciones = normales + personalizadas;
        this.cambioNotificaciones.emit(this.totalNotificaciones);
      },
      error: (err) => {
        console.warn('❌ Error al cargar notificaciones:', err);
      }
    });
  }

  abrir(): void {
    this.clickBadge.emit();
  }
}
