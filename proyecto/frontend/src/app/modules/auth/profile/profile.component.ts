import { Component, OnInit } from '@angular/core';
import { TokenService } from 'src/app/core/services/token.service';
import { User } from 'src/app/core/interfaces/user.interface';
import { AuthService } from '../services/auth.service';
import { JwtResponse } from 'src/app/core/interfaces/JwtResponse';
import { SessionService } from 'src/app/core/services/session.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html'
})
export class ProfileComponent implements OnInit {
  user: User = { id: 0, username: '', email: '', rol: '' };
  errorMsg = '';
  successMsg = '';

  constructor(
    private authService: AuthService,
    private tokenService: TokenService,
    private sessionService: SessionService
  ) {}

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
    next: (res: JwtResponse) => {
      // ✅ Guarda token
      this.tokenService.saveToken(res.token);

      // ✅ Guarda usuario actualizado
      const userGuardado = {
        id: res.id,
        username: res.username,
        email: res.email,
        token: res.token,
        roles: res.roles
      };
      this.tokenService.saveUser(userGuardado);

      // ✅ Notifica a toda la app que el usuario ha cambiado (nav, guards, etc.)
      this.sessionService.notificarInicio();

      // ✅ Mensaje de éxito
      this.successMsg = 'Perfil actualizado correctamente';
      this.errorMsg = '';
    },
    error: () => {
      this.errorMsg = 'Error al actualizar el perfil';
      this.successMsg = '';
    }
  });
}

}
