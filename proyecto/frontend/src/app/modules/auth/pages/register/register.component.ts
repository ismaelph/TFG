import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { SignupRequest } from '../../interfaces/auth';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  registerForm: FormGroup;
  successMsg = '';
  errorMsg = '';
  mostrarPassword: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.registerForm.invalid) return;

    const data: SignupRequest = this.registerForm.value;

    this.authService.signup(data).subscribe({
      next: () => {
        this.successMsg = '¡Usuario registrado con éxito!';
        this.router.navigate(['/auth/login']);
      },
      error: () => {
        this.errorMsg = 'Error al registrar usuario';
      }
    });
  }
}
