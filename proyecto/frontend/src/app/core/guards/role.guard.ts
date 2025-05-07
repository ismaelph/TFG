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

    const payload = JSON.parse(atob(token.split('.')[1]));
    const userRoles: string[] = payload.roles || [];
    const requiredRole = route.data['role'];

    if (userRoles.includes(requiredRole)) {
      return true;
    }

    this.router.navigate(['/auth/login']);
    return false;
  }
}
