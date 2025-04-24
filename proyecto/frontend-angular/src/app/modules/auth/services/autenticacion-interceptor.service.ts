import { Injectable } from '@angular/core';
import { AutenticacionService } from './autenticacion.service';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AutenticacionInterceptorService implements HttpInterceptor {

  constructor(
    private autenticacionService: AutenticacionService
  ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    let peticion: HttpRequest<any>;

    const tokenJWT = this.autenticacionService.getJwtToken();

    if (tokenJWT == null) {
      peticion = req;
    }
    else {
      peticion = req.clone({
        setHeaders: {
          Authorization: `Bearer ${tokenJWT}`,
        }
      });
    }
    return next.handle(peticion);
  }
}
