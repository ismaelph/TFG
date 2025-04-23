export interface User {
    id: number;
    username: string;
    email: string;
    empresa: string | null;
    password?: string;
}
