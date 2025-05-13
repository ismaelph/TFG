import { environment } from '../../../environments/environment';

// Tokens
export const TOKEN_KEY = 'invecta-token';
export const USER_KEY = 'invecta-user';

// Endpoints
export const LOGIN_ENDPOINT = environment.apiUrl + '/api/auth/login';
export const REGISTER_ENDPOINT = environment.apiUrl + '/api/auth/signup';
export const EMPRESA_ENDPOINT = environment.apiUrl + '/api/empresas';
export const CATEGORIA_ENDPOINT = environment.apiUrl + '/api/categorias';
export const PROVEEDOR_ENDPOINT = environment.apiUrl + '/api/proveedores';

// Roles
export const ROLE_ADMIN = 'ROLE_ADMIN';
export const ROLE_ADMIN_EMPRESA = 'ROLE_ADMIN_EMPRESA';
export const ROLE_EMPLEADO = 'ROLE_EMPLEADO';
export const ROLE_USER = 'ROLE_USER';
