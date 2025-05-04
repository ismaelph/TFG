import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { EmpresasRoutingModule } from './empresas-routing.module';
import { InventarioComponent } from './inventario/inventario.component';
import { DashboardrComponent } from './dashboardr/dashboardr.component';


@NgModule({
  declarations: [
    InventarioComponent,
    DashboardrComponent
  ],
  imports: [
    CommonModule,
    EmpresasRoutingModule
  ]
})
export class EmpresasModule { }
