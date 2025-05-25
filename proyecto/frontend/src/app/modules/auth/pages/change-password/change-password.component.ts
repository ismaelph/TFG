import { Component } from '@angular/core';
import { TokenService } from 'src/app/core/services/token.service';
import { CambiarPasswordDto } from '../../interfaces/auth';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html'
})
export class ChangePasswordComponent {
  passwordForm = {
    passwordActual: '',
    nuevaPassword: '',
    repetirPassword: ''
  };

  errorPasswordMsg = '';
  successPasswordMsg = '';

  constructor(
    private authService: AuthService,
    private tokenService: TokenService
  ) {}

  cambiarPassword(): void {
    this.errorPasswordMsg = '';
    this.successPasswordMsg = '';

    const { passwordActual, nuevaPassword, repetirPassword } = this.passwordForm;

    if (!passwordActual || !nuevaPassword || !repetirPassword) {
      this.errorPasswordMsg = 'Todos los campos son obligatorios.';
      return;
    }

    if (nuevaPassword !== repetirPassword) {
      this.errorPasswordMsg = 'Las contraseñas no coinciden.';
      return;
    }

    const id = this.tokenService.getUserId();
    if (id === null) {
      this.errorPasswordMsg = 'No se pudo obtener el ID del usuario.';
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
      error: (err) => {
        console.error('❌ Error al cambiar contraseña:', err);
        this.errorPasswordMsg = err?.error?.message || 'Error al cambiar la contraseña.';
      }
    });
  }
}
