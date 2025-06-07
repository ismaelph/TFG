import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { EmpleadoRoutingModule } from './empleado-routing.module';
import { SharedModule } from 'src/app/shared/shared.module';
import { MiInventarioPersonalComponent } from './inventario/pages/mi-inventario-personal/mi-inventario-personal.component';
import { TarjetaProductoComponent } from './inventario/components/tarjeta-producto/tarjeta-producto.component';
import { FormularioSolicitudComponent } from './inventario/components/formulario-solicitud/formulario-solicitud.component';
import { BannerEstadoComponent } from './inventario/components/banner-estado/banner-estado.component';
import { MiInventarioComponent } from './inventario/pages/mi-inventario/mi-inventario.component';
import { FormularioPersonalizadaComponent } from './inventario/components/formulario-personalizada/formulario-personalizada.component';


@NgModule({
  declarations: [
    MiInventarioPersonalComponent,
    TarjetaProductoComponent,
    FormularioSolicitudComponent,
    BannerEstadoComponent,
    MiInventarioComponent,
    FormularioPersonalizadaComponent,
  ],
  imports: [
    CommonModule,
    EmpleadoRoutingModule,
    FormsModule,
    SharedModule,
    ReactiveFormsModule
  ]
})
export class EmpleadoModule { }
