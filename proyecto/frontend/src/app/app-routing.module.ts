import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { RoleGuard } from './core/guards/role.guard';
import {
  ROLE_ADMIN,
  ROLE_ADMIN_EMPRESA,
  ROLE_EMPLEADO,
  ROLE_USER
} from './core/constants/constants';

const routes: Routes = [
  // Redirección inicial
  { path: '', redirectTo: 'main', pathMatch: 'full' },

  // ADMIN (requiere login + rol admin)
  {
    path: 'admin',
    loadChildren: () =>
      import('./modules/admin/admin.module').then((m) => m.AdminModule),
    canActivate: [AuthGuard, RoleGuard],
    data: { role: ROLE_ADMIN }
  },

  // ADMIN EMPRESA (puede entrar ROLE_USER para crear empresa)
  {
    path: 'empresa',
    loadChildren: () =>
      import('./modules/empresa-admin/empresa-admin.module').then(
        (m) => m.EmpresaAdminModule
      ),
    canActivate: [AuthGuard, RoleGuard],
    data: { role: [ROLE_USER, ROLE_ADMIN_EMPRESA] }
  },

  // EMPLEADO (inventario y mi-inventario)
  {
    path: 'empleado',
    loadChildren: () =>
      import('./modules/empleado/empleado.module').then(
        (m) => m.EmpleadoModule
      ),
    canActivate: [AuthGuard, RoleGuard],
    data: { role: [ROLE_EMPLEADO, ROLE_ADMIN_EMPRESA] }
  },

  // PÁGINA PRINCIPAL (pública)
  {
    path: 'main',
    loadChildren: () =>
      import('./modules/main/main.module').then((m) => m.MainModule)
  },

  // AUTENTICACIÓN
  {
    path: 'auth',
    loadChildren: () =>
      import('./modules/auth/auth.module').then((m) => m.AuthModule)
  },

  // RUTA DESCONOCIDA
  { path: '**', redirectTo: 'main' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
