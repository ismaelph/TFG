import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { EmpresaAdminRoutingModule } from './empresa-admin-routing.module';

import { SharedModule } from 'src/app/shared/shared.module'; // ✅ Módulo con tabla, paginator, navbar, footer

import { EmpresaListComponent } from './pages/empresa-list/empresa-list.component';
import { EmpresaFormComponent } from './pages/empresa-form/empresa-form.component';
import { EmpresaCreateComponent } from './pages/empresa-create/empresa-create.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';

import { CategoriaListComponent } from './pages/categoria-list/categoria-list.component';
import { CategoriaCreateComponent } from './pages/categoria-create/categoria-create.component';

import { ProveedorListComponent } from './pages/proveedor-list/proveedor-list.component';
import { ProveedorCreateComponent } from './pages/proveedor-create/proveedor-create.component';

import { MovimientoListComponent } from './pages/movimiento-list/movimiento-list.component';

import { ProductoListComponent } from './pages/producto-list/producto-list.component';
import { ProductoCreateComponent } from './pages/producto-create/producto-create.component';

@NgModule({
  declarations: [
    EmpresaListComponent,
    EmpresaFormComponent,
    EmpresaCreateComponent,
    DashboardComponent,
    CategoriaListComponent,
    CategoriaCreateComponent,
    ProveedorListComponent,
    ProveedorCreateComponent,
    MovimientoListComponent,
    ProductoListComponent,
    ProductoCreateComponent
  ],
  imports: [
    CommonModule,
    EmpresaAdminRoutingModule,
    RouterModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule // ✅ Aquí llegan app-table y app-paginator
  ]
})
export class EmpresaAdminModule {}
