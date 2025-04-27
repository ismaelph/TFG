import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  // REDIRIGE AL INICIO
  { 
    path: '', redirectTo: '/main/inicio', pathMatch: 'full' 
  }, 

  // RUTA DEL MAIN
  {
    path: 'main', loadChildren: () => import('./modules/main/main.module').then(m => m.MainModule)
  },

  // RUTA DEL ADMIN
  {
    path: 'admin', loadChildren: () => import('./modules/admin/admin.module').then(m => m.AdminModule)
  },

  // RUTA DEL AUTH
  {
    path: 'auth', loadChildren: () => import('./modules/auth/auth.module').then(m => m.AuthModule)
  },


  // ESTA RUTA ES POR SI NO ENCUENTRA NINGUNA RUTA
  // REDIRIGE AL INICIO
  { 
    path: '**', redirectTo: '/main/inicio', pathMatch: 'full' 
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
