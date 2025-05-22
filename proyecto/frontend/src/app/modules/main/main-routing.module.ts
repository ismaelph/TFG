import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InicioComponent } from './inicio/inicio.component';
import { EmpresaJoinComponent } from './empresa-join/empresa-join.component';

const routes: Routes = [
  // REDIRIGE AL INICIO AUTOMÁTICAMENTE
  { 
    path: '', redirectTo: 'inicio', pathMatch: 'full' // Cambiado de '/inicio' a 'inicio'
  },

  // RUTA DEL INICIO
  {
    path: '', component: InicioComponent
  },

  // VER EMPRESA
  { path: 'ver-empresa', component: EmpresaJoinComponent },

  // ESTA RUTA ES POR SI NO ENCUENTRA NINGUNA RUTA
  // REDIRIGE AL INICIO
  {
    path: '**', redirectTo: 'inicio', pathMatch: 'full' // Cambiado de '/inicio' a 'inicio'
  }

  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }
