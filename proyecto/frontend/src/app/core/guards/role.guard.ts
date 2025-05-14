import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, Router } from '@angular/router';
import { TokenService } from '../services/token.service';

@Injectable({
  providedIn: 'root'
})
export class RoleGuard implements CanActivate {

  constructor(private tokenService: TokenService, private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const token = this.tokenService.getToken();

    if (!token) {
      this.router.navigate(['/auth/login']);
      return false;
    }

    // Decodificar el token y obtener los roles
    const payload = JSON.parse(atob(token.split('.')[1]));
    const userRoles: string[] = payload.roles || [];

    // Obtener los roles requeridos desde data['role']
    const requiredRoles = route.data['role'];

    // Normalizar: si es string lo pasamos a array
    const expectedRoles: string[] = Array.isArray(requiredRoles) ? requiredRoles : [requiredRoles];

    const autorizado = userRoles.some(role => expectedRoles.includes(role));

    if (autorizado) {
      return true;
    }

    this.router.navigate(['/auth/login']);
    return false;
  }
}
