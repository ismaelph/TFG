export interface InventarioPersonal {
  productoId: number;
  nombre: string;
  cantidad: number;
  categoriaNombre: string;
  proveedorNombre: string;
  imagenUrl?: string;
  ubicacion?: string;
}
