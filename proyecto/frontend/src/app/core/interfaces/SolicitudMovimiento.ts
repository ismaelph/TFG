export interface SolicitudMovimiento {
  id?: number;
  productoId: number;
  cantidadSolicitada: number;
  motivo: string;
  estado?: string;
  fechaSolicitud?: string;
  fechaResolucion?: string;
  respuestaAdmin?: string;

  // opcionales para visualización
  productoNombre?: string;
  usuarioNombre?: string;
}
