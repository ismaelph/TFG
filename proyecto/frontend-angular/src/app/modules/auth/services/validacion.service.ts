import { Injectable } from '@angular/core';
import { FormControl, ValidationErrors } from '@angular/forms';
import { ConfiguracionServiceService } from 'src/app/main/services/configuracion-service.service';

@Injectable({
  providedIn: 'root'
})
export class ValidacionService {

  private mensajesError: any = {
    correoNoValido: "El correo no pertenece a una institucion registrada"

  }

  constructor() { }

  //---------------------------------------------------------------
  // Funciones para obtener el texto de los errores
  //---------------------------------------------------------------
  getMensajeError(error: string): string {
    return this.mensajesError[error];
  }

  // Permite a otras clases de validación añadir sus mensajes aquí
  registrarMensajeError(clave: string, valor: string) {
    this.mensajesError[clave] = valor;
  }

  validarDominioCorreo(control: FormControl): ValidationErrors | null {
    // Almacena los dominios permitidos desde la configuración
    const dominio: any = ConfiguracionServiceService.getParametro('dominios-permitidos');

    // Convierte el string de dominios en un array
    const dominioArray: string[] = dominio.split(',');

    // Almacena el correo obtenido por el formulario
    const correo: string = control.value;

    // Extrae el dominio del correo (lo que está después del '@')
    const dominioCorreo = correo.split('@')[1];

    // Comprueba si el dominio del correo está en la lista de dominios permitidos
    if (!dominioArray.includes(dominioCorreo)) {
      return {
        correoNoValido: true
      };
    }
    // Si la validación pasa, devuelve null
    return null;
  }
}
