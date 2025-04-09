import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'login', component: LoginComponent
      },
      {
        path: 'signup', component: SignupComponent
      },
      {
        // Por defecto, envía al login.
        path: '**',
        redirectTo: 'login'
      },
    ]

  }

];
@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
