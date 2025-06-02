export interface Producto {
  id?: number;
  nombre: string;
  precio: number;
  cantidad: number;
  usoInterno: boolean;
  fechaIngreso?: string;

  stockMinimo: number;
  imagenUrl?: string | null; 

  empresaId: number;
  categoriaId: number | null;
  proveedorId: number | null;
  usuarioId: number;
  estanteriaId: number;

  categoriaNombre?: string;
  proveedorNombre?: string;
  ubicacion?: string; // Almacén → Planta → Estantería
}
