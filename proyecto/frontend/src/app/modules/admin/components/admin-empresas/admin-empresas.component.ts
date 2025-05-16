import { Component, Input, OnInit } from '@angular/core';
import { Empresa } from 'src/app/core/interfaces/empresa';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-admin-empresas',
  templateUrl: './admin-empresas.component.html',
  styleUrls: ['./admin-empresas.component.css']
})
export class AdminEmpresasComponent implements OnInit {
  @Input() desdeDashboard: boolean = false;

  empresas: Empresa[] = [];
  paginaActual: number = 1;
  elementosPorPagina: number = 5;

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.adminService.getEmpresas().subscribe(data => {
      this.empresas = data;
    });
  }

  get empresasPaginadas(): Empresa[] {
    if (this.desdeDashboard) {
      return this.empresas.slice(0, 2); // mostrar solo 2 en dashboard
    }
    const inicio = (this.paginaActual - 1) * this.elementosPorPagina;
    return this.empresas.slice(inicio, inicio + this.elementosPorPagina);
  }

  cambiarPagina(pagina: number): void {
    this.paginaActual = pagina;
  }
}
