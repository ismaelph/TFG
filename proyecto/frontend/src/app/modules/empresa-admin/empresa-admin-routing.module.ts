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
import { ProductoListComponent } from './pages/producto-list/producto-list.component';
import { ProductoCreateComponent } from './pages/producto-create/producto-create.component';
import { MovimientoListComponent } from './pages/movimiento-list/movimiento-list.component';
import { UsuariosListComponent } from './pages/usuarios-list/usuarios-list.component';
import { AlmacenListComponent } from './pages/almacen-list/almacen-list.component';

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

  // PRODUCTOS
  { path: 'producto-list', component: ProductoListComponent },
  { path: 'producto-create', component: ProductoCreateComponent },

  // MOVIMIENTOS
  { path: 'movimiento-list', component: MovimientoListComponent },


  // USUARIOS
  { path: 'usuarios-list', component: UsuariosListComponent },

  // ALMACEN
  { path: 'almacen-list', component: AlmacenListComponent },

  // Fallback
  { path: '**', redirectTo: 'dashboard' }

  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EmpresaAdminRoutingModule { }
