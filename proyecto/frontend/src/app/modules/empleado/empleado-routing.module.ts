import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MiInventarioComponent } from './inventario/pages/mi-inventario/mi-inventario.component';
import { MiInventarioPersonalComponent } from './inventario/pages/mi-inventario-personal/mi-inventario-personal.component';

const routes: Routes = [
  // 📦 Inventario
  { path: 'inventario', component: MiInventarioComponent },
  { path: 'mi-inventario', component: MiInventarioPersonalComponent },

  // Redirecciones
  { path: '', redirectTo: 'inventario', pathMatch: 'full' },
  { path: '**', redirectTo: 'inventario' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmpleadoRoutingModule { }
