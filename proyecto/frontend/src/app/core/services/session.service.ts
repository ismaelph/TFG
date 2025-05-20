import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private sesionIniciadaSubject = new BehaviorSubject<boolean>(this.tokenService.isLogged());

    public cambios$ = this.sesionIniciadaSubject.asObservable();

  constructor(private tokenService: TokenService) {}

  cerrarSesion(): void {
    this.tokenService.clearAll();
    this.sesionIniciadaSubject.next(false);
  }

  notificarInicio(): void {
    this.sesionIniciadaSubject.next(true);
  }

  notificarCierre(): void {
    this.sesionIniciadaSubject.next(false);
  }

  isSesionIniciada(): Observable<boolean> {
    return this.sesionIniciadaSubject.asObservable();
  }

}
