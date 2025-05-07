import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { RoleGuard } from './core/guards/role.guard';
import { ROLE_ADMIN,ROLE_ADMIN_EMPRESA,ROLE_EMPLEADO} from './core/constants/constants';

const routes: Routes = [
  // Redirección inicial
  { path: '', redirectTo: 'main', pathMatch: 'full' },

  // ADMIN (requiere estar logueado + rol)
  {
    path: 'admin',
    loadChildren: () => import('./modules/admin/admin.module').then(m => m.AdminModule),
    canActivate: [AuthGuard, RoleGuard],
    data: { role: ROLE_ADMIN }
  },

  // ADMIN EMPRESA
  {
    path: 'empresa',
    loadChildren: () => import('./modules/empresa-admin/empresa-admin.module').then(m => m.EmpresaAdminModule),
    canActivate: [AuthGuard, RoleGuard],
    data: { role: ROLE_ADMIN_EMPRESA }
  },

  // EMPLEADO
  {
    path: 'empleado',
    loadChildren: () => import('./modules/empleado/empleado.module').then(m => m.EmpleadoModule),
    canActivate: [AuthGuard, RoleGuard],
    data: { role: ROLE_EMPLEADO }
  },

  // PÁGINA PÚBLICA
  {
    path: 'main',
    loadChildren: () => import('./modules/main/main.module').then(m => m.MainModule)
  },

  // AUTH (login / signup)
  {
    path: 'auth',
    loadChildren: () => import('./modules/auth/auth.module').then(m => m.AuthModule)
  },

  // RUTA POR DEFECTO
  { path: '**', redirectTo: 'main' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
