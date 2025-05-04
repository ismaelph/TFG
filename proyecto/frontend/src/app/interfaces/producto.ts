import { Categoria } from './categoria';
import { Empresa } from './empresa';
import { Proveedor } from './proveedor';
import { Usuario } from './usuario';

export interface Producto {
  id: number;
  nombre: string;
  descripcion?: string;
  precio?: number;
  cantidad: number;
  usoInterno: boolean;
  categoria: Categoria;
  empresa: Empresa;
  proveedor?: Proveedor;
  creadoPor: Usuario;
  createdAt: string;
  updatedAt: string;
}