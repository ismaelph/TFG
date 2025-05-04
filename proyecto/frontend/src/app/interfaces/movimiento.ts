import { Producto } from './producto';
import { Usuario } from './usuario';
import { Empresa } from './empresa';

export interface Movimiento {
  id: number;
  tipo: string; // Ejemplo: "ENTRADA" o "SALIDA"
  cantidad: number;
  producto: Producto;
  usuario: Usuario;
  empresa: Empresa;
  createdAt: string;
  updatedAt: string;
}