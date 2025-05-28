import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


import { MisSolicitudesMovimientoComponent } from './solicitudes/pages/mis-solicitudes-movimiento/mis-solicitudes-movimiento.component';
import { MisSolicitudesPersonalizadaComponent } from './solicitudes/pages/mis-solicitudes-personalizada/mis-solicitudes-personalizada.component';

import { MiInventarioComponent } from './inventario/pages/mi-inventario/mi-inventario.component';
import { MiInventarioPersonalComponent } from './inventario/pages/mi-inventario-personal/mi-inventario-personal.component';

const routes: Routes = [
  // 📦 Inventario
  { path: 'inventario', component: MiInventarioComponent },
  { path: 'mi-inventario', component: MiInventarioPersonalComponent },

  // 📨 Solicitudes
  { path: 'mis-solicitudes/movimiento', component: MisSolicitudesMovimientoComponent },
  { path: 'mis-solicitudes/personalizada', component: MisSolicitudesPersonalizadaComponent },

  // Redirecciones
  { path: '', redirectTo: 'inventario', pathMatch: 'full' },
  { path: '**', redirectTo: 'inventario' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmpleadoRoutingModule { }
