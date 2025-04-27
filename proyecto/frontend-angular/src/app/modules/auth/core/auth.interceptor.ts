import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AutenticacionService } from 'src/app/modules/auth/services/autenticacion.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private autenticacionService: AutenticacionService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.autenticacionService.getToken();

    if (token) {
      // Clonar la solicitud y agregar el token al encabezado Authorization
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      });
    }

    return next.handle(request);
  }
}