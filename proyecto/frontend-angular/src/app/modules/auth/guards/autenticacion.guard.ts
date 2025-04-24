import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanLoad, Route, Router, RouterStateSnapshot, UrlSegment, UrlTree } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { AutenticacionService } from '../services/autenticacion.service';

@Injectable({
  providedIn: 'root'
})
export class AutenticacionGuard implements CanActivate, CanLoad {
  constructor(

    private autenticacionService: AutenticacionService,
    private router: Router

  ) { }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.autenticacionService.isSesionIniciada()
      .pipe(
        tap(autentificado => {
          if (!autentificado) {
            this.router.navigate(['/auth/login']);
          };

          return autentificado;
        })
      );
  }

  canLoad(
    route: Route,
    segments: UrlSegment[]): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {

    return this.autenticacionService.isSesionIniciada()
      .pipe(
        tap(autentificado => {
          if (!autentificado) {
            this.router.navigate(['/auth/login']);
          };

          return autentificado;
        })
      );
  };

}
