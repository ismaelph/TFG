import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  // REDIRIGE AL INICIO
  { 
    path: '', redirectTo: '/main', pathMatch: 'full' 
  }, 

  // RUTA DEL MAIN
  {
    path: 'main', loadChildren: () => import('./modules/main/main.module').then(m => m.MainModule)
  },

  // RUTA DEL ADMIN
  {
    path: 'admin', loadChildren: () => import('./modules/admin/admin.module').then(m => m.AdminModule)
  },


  // ESTA RUTA ES POR SI NO ENCUENTRA NINGUNA RUTA
  // REDIRIGE AL INICIO
  { 
    path: '**', redirectTo: '/main', pathMatch: 'full' 
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
