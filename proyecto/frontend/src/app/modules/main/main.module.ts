import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MainRoutingModule } from './main-routing.module';
import { EmpresaJoinComponent } from './empresa-join/empresa-join.component';
import { InicioComponent } from './inicio/inicio.component';


@NgModule({
  declarations: [
    EmpresaJoinComponent,
    InicioComponent
  ],
  imports: [
    CommonModule,
    MainRoutingModule,
    FormsModule
  ]
})
export class MainModule { }
