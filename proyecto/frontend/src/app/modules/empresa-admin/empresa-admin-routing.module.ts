import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmpresaListComponent } from './pages/empresa-list/empresa-list.component';
import { EmpresaFormComponent } from './pages/empresa-form/empresa-form.component';
import { EmpresaCreateComponent } from './pages/empresa-create/empresa-create.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { CategoriaListComponent } from './pages/categoria-list/categoria-list.component';
import { CategoriaCreateComponent } from './pages/categoria-create/categoria-create.component';
import { ProveedorListComponent } from './pages/proveedor-list/proveedor-list.component';
import { ProveedorCreateComponent } from './pages/proveedor-create/proveedor-create.component';

const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full' },

  // DASHBOARD PRINCIPAL
  { path: 'dashboard', component: DashboardComponent },

  // EMPRESA
  { path: 'list', component: EmpresaListComponent },
  { path: 'form', component: EmpresaFormComponent },
  { path: 'form/:id', component: EmpresaFormComponent },
  { path: 'crear', component: EmpresaCreateComponent },

  // CATEGORÍA
  { path: 'categoria-list', component: CategoriaListComponent },
  { path: 'categoria-create', component: CategoriaCreateComponent },
  { path: 'categoria-edit/:id', component: CategoriaCreateComponent },

  // PROVEEDOR
  { path: 'proveedor-list', component: ProveedorListComponent },
  { path: 'proveedor-create', component: ProveedorCreateComponent },
  { path: 'proveedor-edit/:id', component: ProveedorCreateComponent },

  // Fallback
  { path: '**', redirectTo: 'dashboard' }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmpresaAdminRoutingModule { }
