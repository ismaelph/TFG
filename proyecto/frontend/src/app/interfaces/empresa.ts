import { Usuario } from './usuario';

export interface Empresa {
  id: number;
  nombre: string;
  password: string;
  admins: Usuario[];
}