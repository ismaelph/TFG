import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { InventarioComponent } from './inventario/inventario.component';
import { MiInventarioComponent } from './mi-inventario/mi-inventario.component';

const routes: Routes = [
  { path: 'inventario', component: InventarioComponent },
  { path: 'mi-inventario', component: MiInventarioComponent },
  { path: '', redirectTo: 'inventario', pathMatch: 'full' },
  { path: '**', redirectTo: 'inventario' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmpleadoRoutingModule { }
