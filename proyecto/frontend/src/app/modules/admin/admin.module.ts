import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';


import { AdminRoutingModule } from './admin-routing.module';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AdminUsuariosComponent } from './components/admin-usuarios/admin-usuarios.component';
import { AdminSistemaComponent } from './components/admin-sistema/admin-sistema.component';
import { AdminEmpresasComponent } from './components/admin-empresas/admin-empresas.component';
import { AdminEstadisticasComponent } from './components/admin-estadisticas/admin-estadisticas.component';
import { AdminCorreoGlobalComponent } from './components/admin-correo-global/admin-correo-global.component';
import { SharedModule } from 'src/app/shared/shared.module';


@NgModule({
  declarations: [
    DashboardComponent,
    AdminUsuariosComponent,
    AdminSistemaComponent,
    AdminEmpresasComponent,
    AdminEstadisticasComponent,
    AdminCorreoGlobalComponent,
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    RouterModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule
  ]
})
export class AdminModule { }
