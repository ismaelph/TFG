import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { EstadoSistema } from 'src/app/core/interfaces/estado-sistema.interface';

@Component({
  selector: 'app-admin-sistema',
  templateUrl: './admin-sistema.component.html',
  styleUrls: ['./admin-sistema.component.css']
})
export class AdminSistemaComponent implements OnInit {
  estado: EstadoSistema | null = null;

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.adminService.getEstadoSistema().subscribe(data => {
      this.estado = data;
    });
  }
}
