import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { EmpleadoRoutingModule } from './empleado-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { MiInventarioPersonalComponent } from './inventario/pages/mi-inventario-personal/mi-inventario-personal.component';
import { TarjetaProductoComponent } from './inventario/components/tarjeta-producto/tarjeta-producto.component';
import { FormularioSolicitudComponent } from './inventario/components/formulario-solicitud/formulario-solicitud.component';
import { BannerEstadoComponent } from './inventario/components/banner-estado/banner-estado.component';
import { MisSolicitudesMovimientoComponent } from './solicitudes/pages/mis-solicitudes-movimiento/mis-solicitudes-movimiento.component';
import { MisSolicitudesPersonalizadaComponent } from './solicitudes/pages/mis-solicitudes-personalizada/mis-solicitudes-personalizada.component';
import { FormularioPersonalizadaComponent } from './solicitudes/components/formulario-personalizada/formulario-personalizada.component';
import { NotificacionBadgeComponent } from './notificaciones/components/notificacion-badge/notificacion-badge.component';
import { NotificacionModalComponent } from './notificaciones/components/notificacion-modal/notificacion-modal.component';
import { MiInventarioComponent } from './inventario/pages/mi-inventario/mi-inventario.component';


@NgModule({
  declarations: [
    MiInventarioPersonalComponent,
    TarjetaProductoComponent,
    FormularioSolicitudComponent,
    BannerEstadoComponent,
    MisSolicitudesMovimientoComponent,
    MisSolicitudesPersonalizadaComponent,
    FormularioPersonalizadaComponent,
    NotificacionBadgeComponent,
    NotificacionModalComponent,
    MiInventarioComponent
  ],
  imports: [
    CommonModule,
    EmpleadoRoutingModule,
    FormsModule,
    SharedModule
  ]
})
export class EmpleadoModule { }
