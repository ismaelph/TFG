import { Component, OnInit } from '@angular/core';
import { TokenService } from 'src/app/core/services/token.service';
import { User } from 'src/app/core/interfaces/user.interface';
import { AuthService } from '../../services/auth.service';
import { JwtResponse } from 'src/app/core/interfaces/JwtResponse';
import { CambiarPasswordDto } from '../../interfaces/auth';
import { SessionService } from 'src/app/core/services/session.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html'
})
export class ProfileComponent implements OnInit {
  user: User = { id: 0, username: '', email: '', rol: '' };
  errorMsg = '';
  successMsg = '';

  // Formulario para cambio de contraseña
  passwordForm = {
    passwordActual: '',
    nuevaPassword: '',
    repetirPassword: ''
  };
  errorPasswordMsg = '';
  successPasswordMsg = '';

  constructor(
    private authService: AuthService,
    private tokenService: TokenService,
    private sessionService: SessionService
  ) { }

  ngOnInit(): void {
    const id = this.tokenService.getUserId();
    if (id === null) {
      this.errorMsg = 'No se pudo obtener el ID del usuario';
      return;
    }

    this.authService.getPerfil(id).subscribe({
      next: res => {
        this.user = res;
        console.log('✅ Usuario cargado:', this.user);
      },
      error: () => {
        this.errorMsg = 'Error al cargar el perfil';
        console.error('❌ Error al cargar perfil');
      }
    });
  }

  guardarCambios(): void {
    this.authService.actualizarPerfil(this.user.id!, this.user).subscribe({
      next: (res: JwtResponse) => {
        this.tokenService.saveToken(res.token);

        const userGuardado = {
          id: res.id,
          username: res.username,
          email: res.email,
          token: res.token,
          roles: res.roles
        };
        this.tokenService.saveUser(userGuardado);
        this.sessionService.notificarInicio();

        this.successMsg = 'Perfil actualizado correctamente';
        this.errorMsg = '';
        console.log('✅ Perfil actualizado:', userGuardado);
      },
      error: () => {
        this.errorMsg = 'Error al actualizar el perfil';
        this.successMsg = '';
        console.error('❌ Error al actualizar perfil');
      }
    });
  }

  cambiarPassword(): void {
    this.errorPasswordMsg = '';
    this.successPasswordMsg = '';

    const { passwordActual, nuevaPassword, repetirPassword } = this.passwordForm;

    // Validación básica
    if (!passwordActual || !nuevaPassword || !repetirPassword) {
      this.errorPasswordMsg = 'Todos los campos son obligatorios.';
      return;
    }

    if (nuevaPassword !== repetirPassword) {
      this.errorPasswordMsg = 'Las contraseñas no coinciden.';
      return;
    }

    const id = this.tokenService.getUserId();
    if (!id && id !== 0) {
      this.errorPasswordMsg = 'Error interno: no se pudo obtener el usuario.';
      return;
    }

    const payload: CambiarPasswordDto = {
      passwordActual,
      nuevaPassword
    };

    console.log('📤 Enviando cambio de contraseña:', payload);

    this.authService.cambiarPassword(id, payload).subscribe({
      next: () => {
        console.log('✅ Contraseña cambiada correctamente');
        this.successPasswordMsg = 'Contraseña actualizada correctamente.';
        this.passwordForm = {
          passwordActual: '',
          nuevaPassword: '',
          repetirPassword: ''
        };
      },
      error: (err: any) => {
        console.error('❌ Error al cambiar contraseña:', err);
        this.errorPasswordMsg = err?.error?.message || 'Error al cambiar la contraseña.';
      }
    });
  }

}
