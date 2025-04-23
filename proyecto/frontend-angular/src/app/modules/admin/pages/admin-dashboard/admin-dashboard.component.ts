import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {
  totalUsuarios: number = 0;
  totalEmpresas: number = 0;

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.cargarTotales();
  }

  cargarTotales(): void {
    this.adminService.obtenerUsuarios().subscribe(usuarios => {
      console.log(usuarios);
      this.totalUsuarios = usuarios.length;
    });

    this.adminService.obtenerEmpresas().subscribe(empresas => {
      console.log(empresas);
      this.totalEmpresas = empresas.length;
    });
  }
}
