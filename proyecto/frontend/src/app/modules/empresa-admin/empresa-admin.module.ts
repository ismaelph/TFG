import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

import { EmpresaAdminRoutingModule } from './empresa-admin-routing.module';

import { SharedModule } from 'src/app/shared/shared.module';

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
import { UsuariosListComponent } from './pages/usuarios-list/usuarios-list.component';
import { AlmacenListComponent } from './pages/almacen-list/almacen-list.component';

import { AlmacenCreateComponent } from './components/almacen-create/almacen-create.component';
import { AlmacenEditComponent } from './components/almacen-edit/almacen-edit.component';
import { EstructuraEditComponent } from './components/estructura-edit/estructura-edit.component';
import { ProductoEditComponent } from './components/producto-edit/producto-edit.component';
import { EmpresaCorreoComponent } from './components/empresa-correo/empresa-correo.component';
import { EmpresaCambiarClaveComponent } from './components/empresa-cambiar-clave/empresa-cambiar-clave.component';

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
    ProductoCreateComponent,
    UsuariosListComponent,
    AlmacenListComponent,
    AlmacenCreateComponent,
    AlmacenEditComponent,
    EstructuraEditComponent,
    ProductoEditComponent,
    EmpresaCorreoComponent,
    EmpresaCambiarClaveComponent,
  ],
  imports: [
    CommonModule,
    EmpresaAdminRoutingModule,
    RouterModule,
    ReactiveFormsModule,
    FormsModule,
    SharedModule
  ]
})
export class EmpresaAdminModule { }
