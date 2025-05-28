-- Roles
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_ADMIN', '2025-05-28 11:13:40', '2025-05-28 11:13:40');
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_ADMIN_EMPRESA', '2025-05-28 11:13:40', '2025-05-28 11:13:40');
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_EMPLEADO', '2025-05-28 11:13:40', '2025-05-28 11:13:40');
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_USER', '2025-05-28 11:13:40', '2025-05-28 11:13:40');

-- Empresas
INSERT INTO empresa (id, nombre, clave_acceso, fecha_creacion) VALUES (1, 'SBS Ingeniería', 'sbs123', '2025-05-28 11:13:40');
INSERT INTO empresa (id, nombre, clave_acceso, fecha_creacion) VALUES (2, 'SBS Digital', 'sbsd456', '2025-05-28 11:13:40');

-- Usuarios
INSERT INTO users (id, username, email, password, empresa_id, created_at, updated_at) VALUES (1, 'admin', 'admin@example.com', '$2a$10$bbxuAS5vRkmaEPkG5DHiw.9.ttIT6Ejzs10GGkseWmlzzX5D/9LuG', NULL, '2025-05-28 11:13:40', '2025-05-28 11:13:40');
INSERT INTO users (id, username, email, password, empresa_id, created_at, updated_at) VALUES (2, 'empresa1admin', 'empresa1@empresa.com', '$2a$10$bbxuAS5vRkmaEPkG5DHiw.9.ttIT6Ejzs10GGkseWmlzzX5D/9LuG', 1, '2025-05-28 11:13:40', '2025-05-28 11:13:40');
INSERT INTO users (id, username, email, password, empresa_id, created_at, updated_at) VALUES (3, 'empresa2admin', 'empresa2@empresa.com', '$2a$10$bbxuAS5vRkmaEPkG5DHiw.9.ttIT6Ejzs10GGkseWmlzzX5D/9LuG', 2, '2025-05-28 11:13:40', '2025-05-28 11:13:40');
INSERT INTO users (id, username, email, password, empresa_id, created_at, updated_at) VALUES (4, 'empleado1', 'empleado1@empresa.com', '$2a$10$bbxuAS5vRkmaEPkG5DHiw.9.ttIT6Ejzs10GGkseWmlzzX5D/9LuG', 1, '2025-05-28 11:13:40', '2025-05-28 11:13:40');
INSERT INTO users (id, username, email, password, empresa_id, created_at, updated_at) VALUES (5, 'empleado2', 'empleado2@empresa.com', '$2a$10$bbxuAS5vRkmaEPkG5DHiw.9.ttIT6Ejzs10GGkseWmlzzX5D/9LuG', 2, '2025-05-28 11:13:40', '2025-05-28 11:13:40');
INSERT INTO users (id, username, email, password, empresa_id, created_at, updated_at) VALUES (6, 'userlibre', 'libre@usuario.com', '$2a$10$bbxuAS5vRkmaEPkG5DHiw.9.ttIT6Ejzs10GGkseWmlzzX5D/9LuG', NULL, '2025-05-28 11:13:40', '2025-05-28 11:13:40');

-- Roles de los usuarios
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (4, 3);
INSERT INTO user_roles (user_id, role_id) VALUES (5, 3);
INSERT INTO user_roles (user_id, role_id) VALUES (6, 4);

-- Categoría 1 para Empresa 1
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (1, 'Categoría 1-1', 1, '2025-05-28 11:13:40');
-- Categoría 2 para Empresa 1
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (2, 'Categoría 1-2', 1, '2025-05-28 11:13:40');
-- Categoría 3 para Empresa 1
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (3, 'Categoría 1-3', 1, '2025-05-28 11:13:40');
-- Categoría 4 para Empresa 1
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (4, 'Categoría 1-4', 1, '2025-05-28 11:13:40');
-- Categoría 5 para Empresa 1
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (5, 'Categoría 1-5', 1, '2025-05-28 11:13:40');
-- Categoría 6 para Empresa 1
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (6, 'Categoría 1-6', 1, '2025-05-28 11:13:40');
-- Categoría 7 para Empresa 1
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (7, 'Categoría 1-7', 1, '2025-05-28 11:13:40');
-- Categoría 8 para Empresa 1
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (8, 'Categoría 1-8', 1, '2025-05-28 11:13:40');
-- Categoría 9 para Empresa 1
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (9, 'Categoría 1-9', 1, '2025-05-28 11:13:40');
-- Categoría 10 para Empresa 1
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (10, 'Categoría 1-10', 1, '2025-05-28 11:13:40');
-- Categoría 1 para Empresa 2
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (11, 'Categoría 2-1', 2, '2025-05-28 11:13:40');
-- Categoría 2 para Empresa 2
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (12, 'Categoría 2-2', 2, '2025-05-28 11:13:40');
-- Categoría 3 para Empresa 2
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (13, 'Categoría 2-3', 2, '2025-05-28 11:13:40');
-- Categoría 4 para Empresa 2
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (14, 'Categoría 2-4', 2, '2025-05-28 11:13:40');
-- Categoría 5 para Empresa 2
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (15, 'Categoría 2-5', 2, '2025-05-28 11:13:40');
-- Categoría 6 para Empresa 2
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (16, 'Categoría 2-6', 2, '2025-05-28 11:13:40');
-- Categoría 7 para Empresa 2
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (17, 'Categoría 2-7', 2, '2025-05-28 11:13:40');
-- Categoría 8 para Empresa 2
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (18, 'Categoría 2-8', 2, '2025-05-28 11:13:40');
-- Categoría 9 para Empresa 2
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (19, 'Categoría 2-9', 2, '2025-05-28 11:13:40');
-- Categoría 10 para Empresa 2
INSERT INTO categoria (id, nombre, empresa_id, fecha_creacion) VALUES (20, 'Categoría 2-10', 2, '2025-05-28 11:13:40');
-- Proveedor 1 para Empresa 1
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (1, 'Proveedor 1-1', 'prov1_1@empresa.com', '600000001', 1, '2025-05-28 11:13:40');
-- Proveedor 2 para Empresa 1
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (2, 'Proveedor 1-2', 'prov1_2@empresa.com', '600000002', 1, '2025-05-28 11:13:40');
-- Proveedor 3 para Empresa 1
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (3, 'Proveedor 1-3', 'prov1_3@empresa.com', '600000003', 1, '2025-05-28 11:13:40');
-- Proveedor 4 para Empresa 1
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (4, 'Proveedor 1-4', 'prov1_4@empresa.com', '600000004', 1, '2025-05-28 11:13:40');
-- Proveedor 5 para Empresa 1
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (5, 'Proveedor 1-5', 'prov1_5@empresa.com', '600000005', 1, '2025-05-28 11:13:40');
-- Proveedor 6 para Empresa 1
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (6, 'Proveedor 1-6', 'prov1_6@empresa.com', '600000006', 1, '2025-05-28 11:13:40');
-- Proveedor 7 para Empresa 1
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (7, 'Proveedor 1-7', 'prov1_7@empresa.com', '600000007', 1, '2025-05-28 11:13:40');
-- Proveedor 8 para Empresa 1
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (8, 'Proveedor 1-8', 'prov1_8@empresa.com', '600000008', 1, '2025-05-28 11:13:40');
-- Proveedor 9 para Empresa 1
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (9, 'Proveedor 1-9', 'prov1_9@empresa.com', '600000009', 1, '2025-05-28 11:13:40');
-- Proveedor 10 para Empresa 1
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (10, 'Proveedor 1-10', 'prov1_10@empresa.com', '6000000010', 1, '2025-05-28 11:13:40');
-- Proveedor 1 para Empresa 2
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (11, 'Proveedor 2-1', 'prov2_11@empresa.com', '6000000111', 2, '2025-05-28 11:13:40');
-- Proveedor 2 para Empresa 2
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (12, 'Proveedor 2-2', 'prov2_12@empresa.com', '6000000112', 2, '2025-05-28 11:13:40');
-- Proveedor 3 para Empresa 2
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (13, 'Proveedor 2-3', 'prov2_13@empresa.com', '6000000113', 2, '2025-05-28 11:13:40');
-- Proveedor 4 para Empresa 2
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (14, 'Proveedor 2-4', 'prov2_14@empresa.com', '6000000114', 2, '2025-05-28 11:13:40');
-- Proveedor 5 para Empresa 2
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (15, 'Proveedor 2-5', 'prov2_15@empresa.com', '6000000115', 2, '2025-05-28 11:13:40');
-- Proveedor 6 para Empresa 2
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (16, 'Proveedor 2-6', 'prov2_16@empresa.com', '6000000116', 2, '2025-05-28 11:13:40');
-- Proveedor 7 para Empresa 2
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (17, 'Proveedor 2-7', 'prov2_17@empresa.com', '6000000117', 2, '2025-05-28 11:13:40');
-- Proveedor 8 para Empresa 2
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (18, 'Proveedor 2-8', 'prov2_18@empresa.com', '6000000118', 2, '2025-05-28 11:13:40');
-- Proveedor 9 para Empresa 2
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (19, 'Proveedor 2-9', 'prov2_19@empresa.com', '6000000119', 2, '2025-05-28 11:13:40');
-- Proveedor 10 para Empresa 2
INSERT INTO proveedor (id, nombre, email, telefono, empresa_id, fecha_creacion) VALUES (20, 'Proveedor 2-10', 'prov2_20@empresa.com', '6000000120', 2, '2025-05-28 11:13:40');

-- Productos para SBS Ingeniería
INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id)VALUES ('Producto A1', 10.0, 100, true, '2025-05-28 11:13:40', 1, 1, 1, 2);

INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id)VALUES ('Taladro Industrial', 85.00, 15, true, NOW(), 1, 2, 2, 2);

INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id)VALUES ('Multímetro Digital', 45.50, 30, false, NOW(), 1, 3, 3, 2);

INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id)VALUES ('Caja de Tornillos', 12.00, 200, true, NOW(), 1, 4, 4, 2);

INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id)VALUES ('Cable Eléctrico 100m', 60.00, 40, false, NOW(), 1, 5, 5, 2);


-- Productos para SBS Digital
INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id) VALUES ('Producto B1', 15.0, 90, true, '2025-05-28 11:13:40', 2, 11, 11, 3);
INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id)VALUES ('Disco SSD 1TB', 95.99, 20, true, NOW(), 2, 12, 12, 3);

INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id)VALUES ('Detergente Industrial', 7.50, 60, true, NOW(), 2, 13, 13, 3);

INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id)VALUES ('Guantes Anticorte', 4.80, 100, false, NOW(), 2, 14, 14, 3);
INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id)VALUES ('Monitor Curvo 27"', 179.99, 10, false, NOW(), 2, 15, 15, 3);


-- Movimiento
INSERT INTO movimiento_producto (producto_id, usuario_id, cantidad, tipo, fecha, empresa_id) VALUES (1, 2, 1, 'ENTRADA', '2025-05-28 11:13:40', 1);

-- Almacenes
INSERT INTO almacen (id, nombre, direccion, empresa_id) VALUES (1, 'Almacén Central 1', 'Calle 1', 1);
INSERT INTO almacen (id, nombre, direccion, empresa_id) VALUES (2, 'Almacén Central 2', 'Calle 2', 2);

-- Plantas
INSERT INTO planta (id, nombre, numero, almacen_id) VALUES (1, 'Planta Baja', 0, 1);
INSERT INTO planta (id, nombre, numero, almacen_id) VALUES (2, 'Planta Alta', 1, 1);
INSERT INTO planta (id, nombre, numero, almacen_id) VALUES (3, 'Planta Baja', 0, 2);

-- Estanterías
INSERT INTO estanteria (id, codigo, planta_id) VALUES (1, 'E1-P1', 1);
INSERT INTO estanteria (id, codigo, planta_id) VALUES (2, 'E2-P2', 2);
INSERT INTO estanteria (id, codigo, planta_id) VALUES (3, 'E3-P3', 3);
