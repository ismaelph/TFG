import { Component, OnInit } from '@angular/core';
import { EmpresaService } from '../../services/empresa.service';
import { Empresa } from 'src/app/core/interfaces/empresa';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  empresa: Empresa | null = null;
  mostrarModalEmpresa = false;
  tituloEmpresa: string = '';



  mostrarModalCorreo: boolean = false;

  abrirModalCorreo(): void {
    this.mostrarModalCorreo = true;
  }

  cerrarModalCorreo(): void {
    this.mostrarModalCorreo = false;
  }


  constructor(private empresaService: EmpresaService) { }

  ngOnInit(): void {
    this.cargarEmpresa();
  }

  cargarEmpresa(): void {
    this.empresaService.getMiEmpresa().subscribe({
      next: (data) => {
        this.empresa = data;
        if (this.empresa) {
          this.tituloEmpresa = `Panel de Administración - ${this.empresa.nombre}`;
        }
        else {
          this.tituloEmpresa = 'Panel de Administración';
        }
      },
      error: () => {
        this.empresa = null;
        this.tituloEmpresa = 'Panel de Administración';
      }
    });
  }

  abrirModal(): void {
    this.mostrarModalEmpresa = true;
  }

  cerrarModal(): void {
    this.mostrarModalEmpresa = false;
    this.cargarEmpresa(); // recarga empresa al cerrar modal
  }
}
