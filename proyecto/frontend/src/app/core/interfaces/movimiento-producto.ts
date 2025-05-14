export interface MovimientoProducto {
  id?: number;
  tipo: 'ENTRADA' | 'SALIDA';
  cantidad: number;
  fecha?: string;
  productoId: number;
  usuarioId?: number;
  productoNombre?: string;
  usuarioNombre?: string;
}
