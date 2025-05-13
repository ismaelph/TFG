import { Categoria } from './categoria';
import { Proveedor } from './proveedor';
import { Empresa } from './empresa';

export interface Producto {
  id?: number;
  nombre: string;
  precio?: number;
  cantidad: number;
  usoInterno: boolean;
  fechaIngreso?: string;

  // Relación con entidad Empresa
  empresa?: Empresa;

  // Relación con categoría
  categoria?: Categoria;
  categoriaId?: number;

  // Relación con proveedor
  proveedor?: Proveedor;
  proveedorId?: number;

  // ID del usuario que crea/modifica (si lo usas)
  usuarioId?: number;
}
