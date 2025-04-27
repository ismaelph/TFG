// filepath: c:\Users\pavon\Documents\GitHub\TFG\proyecto\frontend-angular\src\app\modules\client\client.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms'; // Importar FormsModule
import { ClientRoutingModule } from './client-routing.module';
import { ProfileComponent } from './pages/profile/profile.component';

@NgModule({
  declarations: [
    ProfileComponent // Declarar el componente ProfileComponent
  ],
  imports: [
    CommonModule,
    FormsModule, // Agregar FormsModule aquí
    ClientRoutingModule
  ]
})
export class ClientModule { }