import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/interfaces/user.interface';
import { UserService } from '../../services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-usuarios-list',
  templateUrl: './usuarios-list.component.html',
  styleUrls: ['./usuarios-list.component.css']
})
export class UsuariosListComponent implements OnInit {
  usuarios: User[] = [];
  mostrarAlerta = false;
  mensajeAlerta = '';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    // ✅ Espera segura por si hay una desincronización mínima con el token
    setTimeout(() => {
      this.cargarUsuarios();
    }, 100);
  }

  cargarUsuarios(): void {
    this.userService.getUsuariosDeMiEmpresa().subscribe({
      next: (data) => {
        this.usuarios = data;
      },
      error: (err) => {
        console.error('Error al cargar usuarios:', err);
        this.mensajeAlerta = 'No se pudo obtener la lista de usuarios.';
        this.mostrarAlerta = true;
        setTimeout(() => this.mostrarAlerta = false, 4000);
      }
    });
  }

  expulsarUsuario(id: number): void {
    Swal.fire({
      title: '¿Estás seguro?',
      text: 'El usuario será expulsado de la empresa',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#3085d6',
      cancelButtonColor: '#d33',
      confirmButtonText: 'Sí, expulsar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        this.userService.expulsarDeEmpresa(id).subscribe(() => {
          this.usuarios = this.usuarios.filter(u => u.id !== id);
          this.mensajeAlerta = 'Usuario expulsado correctamente.';
          this.mostrarAlerta = true;
          setTimeout(() => this.mostrarAlerta = false, 4000);
        });
      }
    });
  }

}
