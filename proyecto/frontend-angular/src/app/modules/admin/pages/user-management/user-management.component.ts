import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin.service';
import { User } from '../../../../interfaces/user';

@Component({
  selector: 'app-user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.css']
})
export class UserManagementComponent implements OnInit {
  usuarios: User[] = [];
  modalAbierto: boolean = false;
  modalTitulo: string = '';
  usuarioSeleccionado: Partial<User> = {};
  notificacionVisible: boolean = false;
  mensajeNotificacion: string = '';

  constructor(private adminService: AdminService) { }

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.adminService.obtenerUsuarios().subscribe(usuarios => {
      this.usuarios = usuarios;
    });
  }

  abrirModal(accion: 'crear' | 'editar', usuario?: User): void {
    this.modalAbierto = true;
    this.modalTitulo = accion === 'crear' ? 'Crear Usuario' : 'Editar Usuario';
    this.usuarioSeleccionado = usuario ? { ...usuario } : { username: '', email: '', empresa: null, password: '' };
  }

  cerrarModal(): void {
    this.modalAbierto = false;
    this.usuarioSeleccionado = {};
  }

  guardarUsuario(): void {
    if (this.usuarioSeleccionado.id) {
      // Editar usuario
      this.adminService.actualizarUsuario(this.usuarioSeleccionado.id, this.usuarioSeleccionado).subscribe(
        () => {
          this.mostrarNotificacion('Usuario actualizado correctamente');
          this.cargarUsuarios();
          this.cerrarModal();
        },
        (error) => {
          console.error('Error al actualizar el usuario:', error);
          this.mostrarNotificacion('Error al actualizar el usuario');
        }
      );
    } else {
      // Crear usuario
      this.adminService.crearUsuario(this.usuarioSeleccionado).subscribe(
        () => {
          this.mostrarNotificacion('Usuario creado correctamente');
          this.cargarUsuarios();
          this.cerrarModal();
        },
        (error) => {
          console.error('Error al crear el usuario:', error);
          this.mostrarNotificacion('Error al crear el usuario');
        }
      );
    }
  }

  eliminarUsuario(id: number): void {
    this.adminService.eliminarUsuario(id).subscribe(() => {
      this.mostrarNotificacion('Usuario eliminado correctamente');
      this.cargarUsuarios();
    });
  }

  mostrarNotificacion(mensaje: string): void {
    this.mensajeNotificacion = mensaje;
    this.notificacionVisible = true;
    setTimeout(() => {
      this.notificacionVisible = false;
    }, 3000);
  }
}