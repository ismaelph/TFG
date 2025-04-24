import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';

import { AuthRoutingModule } from './auth-routing.module';

import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { GestionarUsuarioComponent } from './pages/gestionar-usuario/gestionar-usuario.component';
import { EnvioCorreoComponent } from './pages/envio-correo/envio-correo.component';
import { CambioContrasenaComponent } from './pages/cambio-contrasena/cambio-contrasena.component';

@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    GestionarUsuarioComponent,
    EnvioCorreoComponent,
    CambioContrasenaComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    AuthRoutingModule,
    ReactiveFormsModule
  ]
})
export class AuthModule { }
