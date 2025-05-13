import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';

import { EmpresaAdminRoutingModule } from './empresa-admin-routing.module';
import { EmpresaListComponent } from './pages/empresa-list/empresa-list.component';
import { EmpresaFormComponent } from './pages/empresa-form/empresa-form.component';
import { EmpresaCreateComponent } from './pages/empresa-create/empresa-create.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { CategoriaListComponent } from './pages/categoria-list/categoria-list.component';
import { CategoriaCreateComponent } from './pages/categoria-create/categoria-create.component';
import { ProveedorListComponent } from './pages/proveedor-list/proveedor-list.component';
import { ProveedorCreateComponent } from './pages/proveedor-create/proveedor-create.component';


@NgModule({
  declarations: [
    EmpresaListComponent,
    EmpresaFormComponent,
    EmpresaCreateComponent,
    DashboardComponent,
    CategoriaListComponent,
    CategoriaCreateComponent,
    ProveedorListComponent,
    ProveedorCreateComponent
  ],
  imports: [
    CommonModule,
    EmpresaAdminRoutingModule,
    RouterModule,
    ReactiveFormsModule,
    FormsModule
  ]
})
export class EmpresaAdminModule { }
