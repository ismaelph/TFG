import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { TableComponent } from './components/table/table.component';
import { PaginatorComponent } from './components/paginator/paginator.component';
import { AlertSuccessComponent } from './components/alerts/alert-success/alert-success.component';
import { AlertWarningComponent } from './components/alerts/alert-warning/alert-warning.component';
import { AlertErrorComponent } from './components/alerts/alert-error/alert-error.component';
import { AlertInfoComponent } from './components/alerts/alert-info/alert-info.component';
import { SidnavComponent } from './components/sidnav/sidnav.component';
import { NotificacionBadgeComponent } from '../modules/empresa-admin/components/notificacion-badge/notificacion-badge.component';
import { NotificacionModalComponent } from '../modules/empresa-admin/components/notificacion-modal/notificacion-modal.component';
import { NotificacionModalEmpleadoComponent } from '../modules/empleado/notificaciones/components/notificacion-modal/notificacion-modal.component';
import { NotificacionBadgeEmpleadosComponent } from '../modules/empleado/notificaciones/components/notificacion-badge-empleados/notificacion-badge-empleados.component';

@NgModule({
  declarations: [
    NavbarComponent,
    FooterComponent,
    TableComponent,
    PaginatorComponent,
    AlertSuccessComponent,
    AlertWarningComponent,
    AlertErrorComponent,
    AlertInfoComponent,
    SidnavComponent,
    NotificacionBadgeComponent,
    NotificacionModalComponent,
    NotificacionModalEmpleadoComponent,
    NotificacionBadgeEmpleadosComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
  ],
  exports: [
    NavbarComponent,
    FooterComponent,
    TableComponent,
    PaginatorComponent,
    CommonModule,
    FormsModule,
    RouterModule,
    AlertSuccessComponent,
    SidnavComponent,
  ]
})
export class SharedModule {}
