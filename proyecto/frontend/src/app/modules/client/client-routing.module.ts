import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProfileComponent } from './pages/profile/profile.component';

const routes: Routes = [
  // Ruta por defecto
  {
    path: '', redirectTo: 'profile', pathMatch: 'full'
  },

  // Ruta del perfil
  {
    path: 'profile', component: ProfileComponent
  },

  // Ruta por si fallan
  {
    path: '**', redirectTo: 'profile', pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientRoutingModule { }
