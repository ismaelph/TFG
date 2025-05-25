import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import Swal from 'sweetalert2';
import { ForgotPasswordRequest } from '../../interfaces/auth';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  form: FormGroup;
  cargando = false;
  errorMsg = '';
  exitoMsg = '';

  constructor(private fb: FormBuilder, private authService: AuthService) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  enviar(): void {
    if (this.form.invalid) {
      this.errorMsg = 'Introduce un correo válido.';
      console.warn('⚠️ Formulario inválido');
      return;
    }

    const payload: ForgotPasswordRequest = { email: this.form.value.email };
    console.log('📤 Payload enviado:', payload);

    this.cargando = true;
    this.errorMsg = '';
    this.exitoMsg = '';

    this.authService.forgotPassword(payload).subscribe({
      next: (res) => {
        console.log('✅ Respuesta del backend:', res);
        this.exitoMsg = res?.message || '¡Correo enviado! Revisa tu bandeja de entrada.';
        this.cargando = false;
      },
      error: (err) => {
        console.error('❌ Error al enviar:', err);
        this.errorMsg = err?.error?.message || 'No se pudo enviar el correo. Intenta de nuevo.';
        this.cargando = false;
      }
    });
  }
}
