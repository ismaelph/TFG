import { Component, OnInit } from '@angular/core';
import { UsuariosService } from '../../services/usuarios.service';
import { EmpresasService } from '../../services/empresas.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  totalUsuarios: number = 0;
  totalEmpresas: number = 0;

  constructor(private usuariosService: UsuariosService, private empresasService: EmpresasService) {}

  ngOnInit(): void {
    this.cargarTotales();
  }

  cargarTotales(): void {
    this.usuariosService.get().subscribe(usuarios => {
      console.log(usuarios);
      this.totalUsuarios = usuarios.length;
    });

    this.empresasService.get().subscribe(empresas => {
      console.log(empresas);
      this.totalEmpresas = empresas.length;
    });
  }
}
