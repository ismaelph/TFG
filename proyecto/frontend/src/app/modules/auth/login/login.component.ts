import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { TokenService } from 'src/app/core/services/token.service';
import { SessionService } from 'src/app/core/services/session.service';
import { Router } from '@angular/router';
import { JwtResponse } from '../interfaces/auth';
import { ROLE_ADMIN, ROLE_ADMIN_EMPRESA, ROLE_EMPLEADO } from 'src/app/core/constants/constants';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMsg: string = '';
  mostrarPassword: boolean = false;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private tokenService: TokenService,
    private sessionService: SessionService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.invalid) return;

    // Limpiar token previo por seguridad
    this.tokenService.clearToken();

    this.authService.login(this.loginForm.value).subscribe({
      next: (res: JwtResponse) => {
        this.tokenService.saveToken(res.token);
        this.tokenService.saveUser(res); // Guardar usuario actual
        this.sessionService.notificarInicio();

        console.log('🆔 Usuario logueado:', res.username);
        console.log('🎭 Roles recibidos:', res.roles);

        // ✅ Refrescar vista para que el navbar se actualice con el nuevo usuario
        setTimeout(() => {
          this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
            this.redirigirPorRol(res.roles);
          });
        }, 50);
      },
      error: err => {
        this.errorMsg = 'Credenciales incorrectas';
        console.error('❌ Error al iniciar sesión:', err);
      }
    });
  }


  redirigirPorRol(roles: string[]) {
    console.log('Roles recibidos:', roles); // 👈 Asegúrate de ver esto
    if (roles.includes(ROLE_ADMIN)) this.router.navigate(['/admin']);
    else if (roles.includes(ROLE_ADMIN_EMPRESA)) this.router.navigate(['/empresa']);
    else if (roles.includes(ROLE_EMPLEADO)) this.router.navigate(['/empleado']);
    else this.router.navigate(['/']);
  }
}
