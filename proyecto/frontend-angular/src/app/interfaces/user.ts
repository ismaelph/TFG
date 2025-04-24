export interface User {
    id: number;
    username: string;
    email: string;
    empresa: string | null;
    password: string;
    created_at: string,
    rol: string,
}
