import { Component, Input, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { User } from 'src/app/core/interfaces/user.interface';

@Component({
  selector: 'app-admin-usuarios',
  templateUrl: './admin-usuarios.component.html',
  styleUrls: ['./admin-usuarios.component.css']
})
export class AdminUsuariosComponent implements OnInit {
  @Input() desdeDashboard: boolean = false;

  usuarios: User[] = [];
  usuariosPaginados: User[] = [];

  paginaActual: number = 1;
  elementosPorPagina: number = 5;

  constructor(private adminService: AdminService) {}

  ngOnInit(): void {
    this.adminService.getUsuarios().subscribe(data => {
      this.usuarios = data;
      this.paginar();
    });
  }

  paginar(): void {
    if (this.desdeDashboard) {
      this.usuariosPaginados = this.usuarios.slice(0, 5);
    } else {
      const inicio = (this.paginaActual - 1) * this.elementosPorPagina;
      this.usuariosPaginados = this.usuarios.slice(inicio, inicio + this.elementosPorPagina);
    }
  }

  onPaginaCambiada(pagina: number): void {
  this.paginaActual = pagina;
  this.paginar(); // ✅ recalcula la página
}

}
