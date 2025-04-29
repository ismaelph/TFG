import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-tabla-generica',
  templateUrl: './tabla-generica.component.html',
  styleUrls: ['./tabla-generica.component.css']
})
export class TablaGenericaComponent {

  // Recibe los datos a mostrar en la tabla
  @Input() datos: any[] = [];

  // Recibe las columnas que se deben mostrar
  @Input() columnas: string[] = [];

  // Emite un evento cuando se desea editar un registro
  @Output() editar = new EventEmitter<any>();

  // Emite un evento cuando se desea eliminar un registro
  @Output() eliminar = new EventEmitter<any>();

  // Función para generar las celdas de la tabla de manera dinámica
  getValor(row: any, column: string) {
    return row[column] || '-';
  }
}
