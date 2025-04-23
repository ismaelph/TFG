import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from './pages/admin-dashboard/admin-dashboard.component';
import { UserManagementComponent } from './pages/user-management/user-management.component';
import { CompanyManagementComponent } from './pages/company-management/company-management.component';

const routes: Routes = [
  { path: '', redirectTo: 'dashboard', pathMatch: 'full'      },
  { path: 'dashboard', component: AdminDashboardComponent     },
  { path: 'users', component: UserManagementComponent         },
  { path: 'companies', component: CompanyManagementComponent  },
  { path: '**', redirectTo: 'dashboard', pathMatch: 'full'    },
  
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
