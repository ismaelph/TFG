export interface Usuario {
  id: number;
  username: string;
  email: string;
  empresa: {
      id: number;
      nombre: string;
  } | null;
  password: string;
  created_at: string;
  rol: string;
  fotoPerfil?: string;
}