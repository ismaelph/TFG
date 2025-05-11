import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';

import { EmpresaAdminRoutingModule } from './empresa-admin-routing.module';
import { EmpresaListComponent } from './pages/empresa-list/empresa-list.component';
import { EmpresaFormComponent } from './pages/empresa-form/empresa-form.component';
import { EmpresaCreateComponent } from './pages/empresa-create/empresa-create.component';


@NgModule({
  declarations: [
    EmpresaListComponent,
    EmpresaFormComponent,
    EmpresaCreateComponent
  ],
  imports: [
    CommonModule,
    EmpresaAdminRoutingModule,
    RouterModule,
    ReactiveFormsModule
  ]
})
export class EmpresaAdminModule { }
