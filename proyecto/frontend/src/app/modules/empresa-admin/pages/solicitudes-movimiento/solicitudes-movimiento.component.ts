import { Component, OnInit } from '@angular/core';
import { SolicitudMovimientoService } from '../../services/solicitud-movimiento-service.service';
import { SolicitudMovimiento } from 'src/app/core/interfaces/SolicitudMovimiento';
import { SolicitudPersonalizada } from 'src/app/core/interfaces/SolicitudPersonalizada';
import { SolicitudPersonalizadaService } from '../../services/solicitud-personalizada-service.service';
import { EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-solicitudes-movimiento',
  templateUrl: './solicitudes-movimiento.component.html',
  styleUrls: ['./solicitudes-movimiento.component.css']
})
export class SolicitudesMovimientoComponent implements OnInit {
  @Output() cerrar = new EventEmitter<void>();
  
    solicitudes: (SolicitudMovimiento | SolicitudPersonalizada)[] = [];
    paginaActual = 1;
    elementosPorPagina = 6;
  
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
        // Filtra por estado PENDIENTE solamente (opcional si backend no lo hace)
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
  
    get solicitudesPaginadas(): (SolicitudMovimiento | SolicitudPersonalizada)[] {
      const inicio = (this.paginaActual - 1) * this.elementosPorPagina;
      return this.solicitudes.slice(inicio, inicio + this.elementosPorPagina);
    }
  
    cambiarPagina(pagina: number): void {
      this.paginaActual = pagina;
    }
  
    // ✅ Nueva detección segura: usamos campos obligatorios
    esSolicitudMovimiento(noti: any): noti is SolicitudMovimiento {
      return 'motivo' in noti && 'productoId' in noti;
    }
  
    getNombreProducto(noti: SolicitudMovimiento | SolicitudPersonalizada): string {
      return this.esSolicitudMovimiento(noti)
        ? noti.productoNombre || 'Producto desconocido'
        : noti.nombreProductoSugerido || 'Sugerencia sin nombre';
    }
  
    getNombreUsuario(noti: SolicitudMovimiento | SolicitudPersonalizada): string {
      return 'usuarioNombre' in noti
        ? noti.usuarioNombre || 'Usuario desconocido'
        : 'Usuario desconocido';
    }
  
    getCantidad(noti: SolicitudMovimiento | SolicitudPersonalizada): number {
      return this.esSolicitudMovimiento(noti)
        ? noti.cantidadSolicitada || 0
        : noti.cantidadDeseada || 0;
    }

    getMotivo(noti: SolicitudMovimiento | SolicitudPersonalizada): string {
      return this.esSolicitudMovimiento(noti)
        ? noti.motivo || 'Motivo no especificado'
        : noti.descripcion || 'Motivo personalizado no especificado';
    }
  
    aceptar(noti: SolicitudMovimiento | SolicitudPersonalizada): void {
      const respuesta = this.esSolicitudMovimiento(noti)
        ? 'Solicitud aprobada. Producto enviado correctamente.'
        : 'Solicitud personalizada aprobada. En espera de stock. Vuelva a intentarlo más adelante.';
  
      try {
        if (this.esSolicitudMovimiento(noti)) {
          console.log(`🟢 Aprobando solicitud NORMAL ID: ${noti.id}`);
          this.movimientoService.resolverSolicitud(noti.id!, true, respuesta).subscribe(() => {
            console.log('✅ Solicitud movimiento resuelta');
            this.cargarSolicitudes();
          });
        } else {
          console.log(`🟢 Aprobando solicitud PERSONALIZADA ID: ${noti.id}`);
          this.personalizadaService.resolverSolicitud(noti.id!, true, respuesta).subscribe(() => {
            console.log('✅ Solicitud personalizada resuelta');
            this.cargarSolicitudes();
          });
        }
      } catch (error) {
        console.error('❌ Error al aprobar la solicitud:', error);
      }
    }
  
    rechazar(noti: SolicitudMovimiento | SolicitudPersonalizada): void {
      const respuesta = 'Lo sentimos, pero su propuesta no ha sido aceptada.';
  
      try {
        if (this.esSolicitudMovimiento(noti)) {
          console.log(`🔴 Rechazando solicitud NORMAL ID: ${noti.id}`);
          this.movimientoService.resolverSolicitud(noti.id!, false, respuesta).subscribe(() => {
            console.log('❌ Solicitud movimiento rechazada');
            this.cargarSolicitudes();
          });
        } else {
          console.log(`🔴 Rechazando solicitud PERSONALIZADA ID: ${noti.id}`);
          this.personalizadaService.resolverSolicitud(noti.id!, false, respuesta).subscribe(() => {
            console.log('❌ Solicitud personalizada rechazada');
            this.cargarSolicitudes();
          });
        }
      } catch (error) {
        console.error('❌ Error al rechazar la solicitud:', error);
      }
    }
}
