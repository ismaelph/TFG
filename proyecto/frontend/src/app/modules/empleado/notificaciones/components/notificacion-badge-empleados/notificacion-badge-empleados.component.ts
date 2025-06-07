import { Component, EventEmitter, OnInit, Output, OnDestroy } from '@angular/core';
import { Subscription, interval, forkJoin } from 'rxjs';
import { SolicitudMovimientoEmpleadoService } from '../../../services/solicitud-movimiento-Empleado.service';
import { SolicitudPersonalizadaEmpleadoService } from '../../../services/solicitud-personalizada-Empleado.service';
import { TokenService } from 'src/app/core/services/token.service';

@Component({
  selector: 'app-notificacion-badge-empleados',
  templateUrl: './notificacion-badge-empleados.component.html',
  styleUrls: ['./notificacion-badge-empleados.component.css']
})
export class NotificacionBadgeEmpleadosComponent implements OnInit, OnDestroy {
  @Output() clickBadge = new EventEmitter<void>();
  @Output() cambioNotificaciones = new EventEmitter<number>();

  totalNotificaciones = 0;
  private actualizadorSub?: Subscription;

  constructor(
    private movService: SolicitudMovimientoEmpleadoService,
    private perService: SolicitudPersonalizadaEmpleadoService,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    this.actualizadorSub = interval(1000).subscribe(() => this.actualizarNotificaciones());
    this.actualizarNotificaciones(); // Carga inicial inmediata
  }

  ngOnDestroy(): void {
    this.actualizadorSub?.unsubscribe();
  }

  actualizarNotificaciones(): void {
    const userId = this.tokenService.getUserId();
    if (!userId) {
      console.warn('⛔ No se encontró userId para cargar notificaciones del empleado');
      return;
    }

    forkJoin({
      movs: this.movService.getNoLeidasEmpleado(userId),
      pers: this.perService.getNoLeidasEmpleado(userId)
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
