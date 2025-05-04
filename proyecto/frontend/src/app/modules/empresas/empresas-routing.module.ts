import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InventarioComponent } from './inventario/inventario.component';
import { DashboardrComponent } from './dashboardr/dashboardr.component';

const routes: Routes = [
  // RUTA BASE
  {
    path: '', redirectTo: 'inventario', pathMatch: 'full'
  },

  // RUTA PRINCIPAL
  {
    path: 'inventario', component: InventarioComponent
  },

  // RUTA DASHBOARD
  {
    path: 'dashboard', component: DashboardrComponent
  },

  // RUTA DEFAULT
  {
    path: '**', redirectTo: 'inventario', pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmpresasRoutingModule { }
