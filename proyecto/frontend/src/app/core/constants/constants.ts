import { environment } from '../../../environments/environment';

// Tokens
export const TOKEN_KEY = 'invecta-token';
export const USER_KEY = 'invecta-user';

// Endpoints Autenticación
export const LOGIN_ENDPOINT = environment.apiUrl + '/api/auth/login';
export const REGISTER_ENDPOINT = environment.apiUrl + '/api/auth/signup';

// Endpoints de la API
export const EMPRESA_ENDPOINT = environment.apiUrl + '/api/empresas';
export const CATEGORIA_ENDPOINT = environment.apiUrl + '/api/categorias';
export const PROVEEDOR_ENDPOINT = environment.apiUrl + '/api/proveedores';
export const PRODUCTO_ENDPOINT = environment.apiUrl + '/api/productos';
export const ALMACEN_ENDPOINT = environment.apiUrl + '/api/almacenes';
export const ESTANTERIA_ENDPOINT = environment.apiUrl + '/api/estanterias';
export const PLANTA_ENDPOINT = environment.apiUrl + '/api/plantas';

// Endpoint Movimientos
export const MOVIMIENTO_ENDPOINT = environment.apiUrl + '/api/movimientos';
export const PERSONALIZADA_ENDPOINT = environment.apiUrl + '/api/solicitudes/personalizada';
export const SOLICITUD_ENDPOINT = environment.apiUrl + '/api/solicitudes/movimiento';

// Endpoint Administración
export const ADMIN_ENDPOINT = environment.apiUrl + '/api/admin';
export const USER_ENDPOINT = environment.apiUrl + '/api/usuarios';

// Roles
export const ROLE_ADMIN = 'ROLE_ADMIN';
export const ROLE_ADMIN_EMPRESA = 'ROLE_ADMIN_EMPRESA';
export const ROLE_EMPLEADO = 'ROLE_EMPLEADO';
export const ROLE_USER = 'ROLE_USER';
