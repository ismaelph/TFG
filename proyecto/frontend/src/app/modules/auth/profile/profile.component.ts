import { Component, OnInit } from '@angular/core';
import { TokenService } from 'src/app/core/services/token.service';
import { User } from 'src/app/core/interfaces/user.interface';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html'
})
export class ProfileComponent implements OnInit {
  user: User = { id: 0, username: '', email: '', rol: '' };
  errorMsg = '';
  successMsg = '';

  constructor(private authService: AuthService, private tokenService: TokenService) {}

  ngOnInit(): void {
  const id = this.tokenService.getUserId();
  if (id === null) {
    this.errorMsg = 'No se pudo obtener el ID del usuario';
    return;
  }

  this.authService.getPerfil(id).subscribe({
    next: res => this.user = res,
    error: () => this.errorMsg = 'Error al cargar el perfil'
  });
}


  guardarCambios(): void {
    this.authService.actualizarPerfil(this.user.id!, this.user).subscribe({
      next: res => this.successMsg = 'Perfil actualizado correctamente',
      error: () => this.errorMsg = 'Error al actualizar el perfil'
    });
  }
}
