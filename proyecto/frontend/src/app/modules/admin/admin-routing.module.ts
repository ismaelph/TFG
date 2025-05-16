import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { AdminUsuariosComponent } from './components/admin-usuarios/admin-usuarios.component';
import { AdminEmpresasComponent } from './components/admin-empresas/admin-empresas.component';

const routes: Routes = [
  // REDIRECTS
  {
    path: '',redirectTo: 'dashboard', pathMatch: 'full'
  },

  // DASHBOARD
  {
    path: 'dashboard', component: DashboardComponent
  },

  // USERS
  {
    path: 'users', component: AdminUsuariosComponent
  },

  // EMPRESAS
  {
    path: 'empresas', component: AdminEmpresasComponent
  },

  // REDIRECTS
  {
    path: '**', redirectTo: 'dashboard', pathMatch: 'full'
  }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
