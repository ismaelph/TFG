import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { EmpleadoRoutingModule } from './empleado-routing.module';
import { InventarioComponent } from './inventario/inventario.component';
import { MiInventarioComponent } from './mi-inventario/mi-inventario.component';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [
    InventarioComponent,
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
