import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import Swal from 'sweetalert2';
import { ResetPasswordRequest } from '../../interfaces/auth';

@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {
  form: FormGroup;
  token: string = '';
  cargando = false;
  errorMsg = '';
  exitoMsg = '';

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {
    this.form = this.fb.group({
      nuevaPassword: ['', [Validators.required, Validators.minLength(6)]],
      repetirPassword: ['', [Validators.required]]
    });
  }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.token = params['token'] || '';
      console.log('📥 Token recibido:', this.token);
    });
  }

  enviar(): void {
    if (this.form.invalid) {
      this.errorMsg = 'Por favor completa todos los campos correctamente.';
      return;
    }

    const nuevaPassword = this.form.value.nuevaPassword;
    const repetirPassword = this.form.value.repetirPassword;

    if (nuevaPassword !== repetirPassword) {
      this.errorMsg = 'Las contraseñas no coinciden.';
      return;
    }

    const payload: ResetPasswordRequest = {
      token: this.token,
      nuevaPassword
    };

    console.log('📤 Enviando reset-password:', payload);
    this.cargando = true;
    this.errorMsg = '';
    this.exitoMsg = '';

    this.authService.resetPassword(payload).subscribe({
      next: () => {
        this.cargando = false;
        this.exitoMsg = '¡Contraseña cambiada correctamente!';
        console.log('✅ Contraseña cambiada');
        Swal.fire('Listo', 'Tu contraseña ha sido restablecida.', 'success');
      },
      error: (err) => {
        this.cargando = false;
        this.errorMsg = 'Error al cambiar contraseña. Intenta de nuevo.';
        console.error('❌ Error al cambiar contraseña:', err);
      }
    });
  }
}
