import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InicioComponent } from './inicio/inicio.component';

const routes: Routes = [

  // REDIRIGE AL INICIO
  { 
    path: '', component: InicioComponent 
  },

  // RUTA DEL INICIO
  {
    path: 'inicio', component: InicioComponent
  },

  // ESTA RUTA ES POR SI NO ENCUENTRA NINGUNA RUTA
  // REDIRIGE AL INICIO
  {
    path: '**', redirectTo: '/main', pathMatch: 'full'
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule { }
