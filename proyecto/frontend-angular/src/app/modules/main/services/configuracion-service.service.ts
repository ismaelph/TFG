import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Configuracion } from '../interface/configuracion.interface';
import { Observable } from 'rxjs';
import { catchError, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ConfiguracionServiceService {

  private static configuracion: Configuracion[] = [];
  private static configuracionCargada = false;
  private URL_BACKEND = `${environment.urlBackendLaravel}/configuracion`;

  constructor(private httpClient: HttpClient) { }

  // Método para obtener un parámetro de configuración
  public static getParametro(parametro: string): string {
    const config = this.configuracion.find(config => config.parametro === parametro);
    if (!config) {
      throw new Error(`El parámetro "${parametro}" no se encuentra en la configuración.`);
    }
    return config.valor;
  }

  // Método que retorna un Observable para cargar la configuración
  private cargarConfiguracion(): Observable<Configuracion[]> {
    return this.httpClient.get<Configuracion[]>(this.URL_BACKEND).pipe(
      map(tablaConfiguracion => {
        ConfiguracionServiceService.configuracion = tablaConfiguracion;
        ConfiguracionServiceService.configuracionCargada = true;
        return tablaConfiguracion;
      }),
      catchError(error => {
        // Manejo de errores en caso de fallo de la carga
        console.error('Error cargando la configuración', error);
        return []; // Devuelve un array vacío o puedes lanzar el error para manejarlo en otro lugar
      })
    );
  }

  // Método llamado por APP_INITIALIZER para cargar la configuración al iniciar
  public inicializarConfiguracion(): Observable<void> {
    return new Observable<void>((observer) => {
      this.cargarConfiguracion().subscribe({
        next: () => observer.next(),
        error: (err) => observer.error(err),
        complete: () => observer.complete()
      });
    });
  }
}
