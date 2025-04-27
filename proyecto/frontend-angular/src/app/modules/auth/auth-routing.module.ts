import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { ProfileComponent } from './pages/profile/profile.component';

const routes: Routes = [
  // RUTAS DEL MÓDULO DE AUTENTICACIÓN

  // RUTA BASE
  {
    path: '', redirectTo: 'login', pathMatch: 'full'
  },

  // RUTA DE LOGIN
  {
    path: 'login', component: LoginComponent
  },

  // RUTA DE REGISTRO
  {
    path: 'signup', component: SignupComponent
  },

  // RUTA DEL PERFIL
  {
    path: 'profile', component: ProfileComponent
  },

  // RUTA POR DEFECTO
  {
    path: '**', redirectTo: 'login', pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
