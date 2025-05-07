import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { TokenService } from './token.service';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private sesionIniciadaSubject = new BehaviorSubject<boolean>(this.tokenService.isLogged());

  constructor(private tokenService: TokenService) {}

  cerrarSesion(): void {
    this.tokenService.clearToken();
    this.sesionIniciadaSubject.next(false);
  }

  notificarInicio(): void {
    this.sesionIniciadaSubject.next(true);
  }

  isSesionIniciada(): Observable<boolean> {
    return this.sesionIniciadaSubject.asObservable();
  }
}
