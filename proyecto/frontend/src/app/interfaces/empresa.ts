export interface Empresa {
    id: number;
    nombre: string;
    password: string;
    creador?: {
      id: number;
      username: string;
    };
  }