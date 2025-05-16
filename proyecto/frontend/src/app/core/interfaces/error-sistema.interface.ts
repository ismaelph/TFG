export interface ErrorSistema {
  fecha: string;
  detalle?: string;
  linea?: string; // por si solo viene una línea sin split
}
