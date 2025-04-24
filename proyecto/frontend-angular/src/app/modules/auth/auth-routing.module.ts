import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { GestionarUsuarioComponent } from './pages/gestionar-usuario/gestionar-usuario.component';
import { EnvioCorreoComponent } from './pages/envio-correo/envio-correo.component';
import { CambioContrasenaComponent } from './pages/cambio-contrasena/cambio-contrasena.component';
import { AutenticacionGuard } from './guards/autenticacion.guard';

const routes: Routes = [
  {
    // No es necesario especificar nada en este atributo
    // Ya que estamos definiendo rutas hijas.
    path: '',
    children: [
      {
        // path para el login de usuarios
        path: 'login',
        component: LoginComponent,
      },
      {
        // path para el registro de usuarios
        path: 'register',
        component: RegisterComponent
      },
      {
        // path para el registro de usuarios
        path: 'gestionar-usuario/:id',
        component: GestionarUsuarioComponent,
        canActivate:[AutenticacionGuard],
        canLoad:[AutenticacionGuard]
      },
      {
        // path para el correo a los usuarios
        path: 'solicitar-restablecimiento',
        component: EnvioCorreoComponent
      },
      { 
        // path para la contraseña a los usuarios
        path: 'cambio-contrasena',
        component: CambioContrasenaComponent 

      },
      {
        // Por defecto, envía al login.
        path: '**',
        redirectTo: 'login'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
