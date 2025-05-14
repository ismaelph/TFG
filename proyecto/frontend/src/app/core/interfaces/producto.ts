import { Categoria } from './categoria';
import { Proveedor } from './proveedor';
import { Empresa } from './empresa';

export interface Producto {
  id?: number;
  nombre: string;
  precio: number;
  cantidad: number;
  usoInterno: boolean;
  fechaIngreso: string;

  empresaId: number;
  categoriaId: number | null;
  proveedorId: number | null;
  usuarioId: number;

  categoriaNombre?: string;
  proveedorNombre?: string;
}


