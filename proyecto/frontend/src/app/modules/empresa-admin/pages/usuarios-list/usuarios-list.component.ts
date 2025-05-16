import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/interfaces/user.interface';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-usuarios-list',
  templateUrl: './usuarios-list.component.html',
  styleUrls: ['./usuarios-list.component.css']
})
export class UsuariosListComponent implements OnInit {
  usuarios: User[] = [];
  mostrarAlerta = false;
  mensajeAlerta = '';

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.cargarUsuarios();
  }

  cargarUsuarios(): void {
    this.userService.getUsuariosDeMiEmpresa().subscribe(data => {
      this.usuarios = data;
    });
  }

  expulsarUsuario(id: number): void {
    this.userService.expulsarDeEmpresa(id).subscribe(() => {
      this.usuarios = this.usuarios.filter(u => u.id !== id);
      this.mensajeAlerta = 'Usuario expulsado correctamente.';
      this.mostrarAlerta = true;
      setTimeout(() => this.mostrarAlerta = false, 4000);
    });
  }
}
