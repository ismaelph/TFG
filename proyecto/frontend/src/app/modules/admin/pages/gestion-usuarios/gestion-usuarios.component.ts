import { Component, OnInit } from '@angular/core';
import { Usuario } from 'src/app/interfaces/usuario';
import { UsuariosService } from '../../services/usuarios.service';

@Component({
  selector: 'app-gestion-usuarios',
  templateUrl: './gestion-usuarios.component.html',
  styleUrls: ['./gestion-usuarios.component.css']
})
export class GestionUsuariosComponent implements OnInit {

  usuarios: Usuario[] = [];
  usuarioEditando: Partial<Usuario> = {};
  mostrarModal: boolean = false;

  constructor(private usuariosService: UsuariosService) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.usuariosService.get().subscribe(data => {
      this.usuarios = data;
    });
  }

  abrirModal(usuario?: Usuario): void {
    this.usuarioEditando = usuario ? { ...usuario } : {};
    this.mostrarModal = true;
  }

  cerrarModal(): void {
    this.mostrarModal = false;
    this.usuarioEditando = {};
  }

  save(): void {
    if (this.usuarioEditando.id) {
      this.usuariosService.put(this.usuarioEditando.id, this.usuarioEditando).subscribe(() => {
        this.cargarUsuarios();
        this.cerrarModal();
      });
    } else {
      this.usuariosService.post(this.usuarioEditando).subscribe(() => {
        this.cargarUsuarios();
        this.cerrarModal();
      });
    }
  }

  eliminarUsuario(id: number): void {
    if (confirm('¿Estás seguro de eliminar este usuario?')) {
      this.usuariosService.delete(id).subscribe(() => {
        this.cargarUsuarios();
      });
    }
  }

}
