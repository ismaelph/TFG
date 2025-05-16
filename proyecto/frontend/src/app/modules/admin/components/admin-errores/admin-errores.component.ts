import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { ErrorSistema } from 'src/app/core/interfaces/error-sistema.interface';

@Component({
  selector: 'app-admin-errores',
  templateUrl: './admin-errores.component.html',
  styleUrls: ['./admin-errores.component.css']
})
export class AdminErroresComponent implements OnInit {
  errores: ErrorSistema[] = [];
  mostrar: number = 3;
  paso: number = 3;

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.adminService.getErroresSistema().subscribe(data => {
      this.errores = data;
    });
  }

  verMas(): void {
    this.mostrar += this.paso;
  }

  verMenos(): void {
    this.mostrar = this.paso;
  }
}
