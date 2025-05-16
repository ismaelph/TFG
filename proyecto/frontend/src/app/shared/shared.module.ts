import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { TableComponent } from './components/table/table.component';
import { PaginatorComponent } from './components/paginator/paginator.component';
import { AlertSuccessComponent } from './components/alerts/alert-success/alert-success.component';
import { AlertWarningComponent } from './components/alerts/alert-warning/alert-warning.component';
import { AlertErrorComponent } from './components/alerts/alert-error/alert-error.component';
import { AlertInfoComponent } from './components/alerts/alert-info/alert-info.component';

@NgModule({
  declarations: [
    NavbarComponent,
    FooterComponent,
    TableComponent,
    PaginatorComponent,
    AlertSuccessComponent,
    AlertWarningComponent,
    AlertErrorComponent,
    AlertInfoComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule
  ],
  exports: [
    NavbarComponent,
    FooterComponent,
    TableComponent,
    PaginatorComponent,
    CommonModule,
    FormsModule,
    RouterModule,
    AlertSuccessComponent,
  ]
})
export class SharedModule {}
