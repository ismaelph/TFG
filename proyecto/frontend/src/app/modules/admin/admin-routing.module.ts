import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { GestionUsuariosComponent } from './pages/gestion-usuarios/gestion-usuarios.component';

const routes: Routes = [
  // REDIRIGE AL DASHBOARD
  {
    path: '', redirectTo: 'dashboard', pathMatch: 'full'
  },

  // RUTA DEL DASHBOARD
  {
    path: 'dashboard', component: DashboardComponent
  },

  // RUTA DE USUARIOS
  {
    path: 'usuarios', component: GestionUsuariosComponent
  },

  // RUTA DE EMPRESAS
  {
    path: 'empresas', component: GestionUsuariosComponent
  },

  // RUTA POR DEFECTO
  // REDIRIGE AL DASHBOARD
  {
    path: '**', redirectTo: 'dashboard', pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
