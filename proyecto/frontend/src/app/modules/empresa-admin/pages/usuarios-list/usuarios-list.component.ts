import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/interfaces/user.interface';
import { UserService } from '../../services/user.service';
import Swal from 'sweetalert2';
import { TokenService } from 'src/app/core/services/token.service';

@Component({
  selector: 'app-usuarios-list',
  templateUrl: './usuarios-list.component.html',
  styleUrls: ['./usuarios-list.component.css']
})
export class UsuariosListComponent implements OnInit {
  usuarios: User[] = [];
  usuariosPaginados: User[] = [];
  itemsPerPage: number = 6;
  currentPage: number = 1;

  mostrarAlerta = false;
  mensajeAlerta = '';

  constructor(
    private userService: UserService,
    private tokenService: TokenService
  ) {}

  ngOnInit(): void {
    setTimeout(() => {
      this.cargarUsuarios();
    }, 100);
  }

  cargarUsuarios(): void {
    const usuarioActual = this.tokenService.getUser();

    this.userService.getUsuariosDeMiEmpresa().subscribe({
      next: (data) => {
        // Filtrar al usuario actual
        this.usuarios = data.filter(u => u.id !== usuarioActual?.id);
        this.cambiarPagina(1); // Mostrar primera página al cargar
      },
      error: (err) => {
        console.error('Error al cargar usuarios:', err);
        this.mensajeAlerta = 'No se pudo obtener la lista de usuarios.';
        this.mostrarAlerta = true;
        setTimeout(() => this.mostrarAlerta = false, 4000);
      }
    });
  }

  cambiarPagina(pagina: number): void {
    this.currentPage = pagina;
    const start = (pagina - 1) * this.itemsPerPage;
    const end = start + this.itemsPerPage;
    this.usuariosPaginados = this.usuarios.slice(start, end);
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
          this.cambiarPagina(this.currentPage); // Actualizar vista paginada
          this.mensajeAlerta = 'Usuario expulsado correctamente.';
          this.mostrarAlerta = true;
          setTimeout(() => this.mostrarAlerta = false, 4000);
        });
      }
    });
  }
}
