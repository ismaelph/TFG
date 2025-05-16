import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { RegistroMensual } from 'src/app/core/interfaces/registro-mensual.interface';
import { User } from 'src/app/core/interfaces/user.interface';

@Component({
  selector: 'app-admin-estadisticas',
  templateUrl: './admin-estadisticas.component.html',
  styleUrls: ['./admin-estadisticas.component.css']
})
export class AdminEstadisticasComponent implements OnInit {
  totalUsuarios: number = 0;
  registros: RegistroMensual[] = [];

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    // Obtener el total real de usuarios
    this.adminService.getUsuarios().subscribe((usuarios: User[]) => {
      this.totalUsuarios = usuarios.length;
    });

    // Obtener el desglose mensual
    this.adminService.getUsuariosPorMes().subscribe((data: RegistroMensual[]) => {
      this.registros = data;
    });
  }
}
