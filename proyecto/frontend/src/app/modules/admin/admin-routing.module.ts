import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';

const routes: Routes = [
  // REDIRECTS
  {
    path: '',redirectTo: 'dashboard', pathMatch: 'full'
  },

  // DASHBOARD
  {
    path: 'dashboard', component: DashboardComponent
  },

  // REDIRECTS
  {
    path: '**', redirectTo: 'dashboard', pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
