export interface Company {
    id: number;
    nombre: string;
    password?: string; // No es recomendable mostrar la contraseña en el frontend
    usuarioCreadorId: number; // ID del usuario que creó la empresa
}
