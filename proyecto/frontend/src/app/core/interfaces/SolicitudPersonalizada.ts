export interface SolicitudPersonalizada {
  id?: number;
  nombreProductoSugerido: string;
  cantidadDeseada: number;
  descripcion: string;
  estado?: string;
  fechaSolicitud?: string;
  fechaResolucion?: string;
  respuestaAdmin?: string;
}
