-- Roles
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_ADMIN_EMPRESA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_EMPLEADO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Empresas
INSERT INTO empresa (id, nombre, clave_acceso, fecha_creacion) VALUES (1, 'SBS Ingeniería', 'sbs123', NOW());
INSERT INTO empresa (id, nombre, clave_acceso, fecha_creacion) VALUES (2, 'SBS Digital', 'sbsd456', NOW());

-- Usuarios
INSERT INTO users (id, username, email, password, empresa_id, created_at, updated_at) VALUES (1, 'admin', 'admin@example.com', '$2a$10$bbxuAS5vRkmaEPkG5DHiw.9.ttIT6Ejzs10GGkseWmlzzX5D/9LuG', null, NOW(), NOW()); -- 12345678
INSERT INTO users (id, username, email, password, empresa_id, created_at, updated_at) VALUES (2, 'empresa1admin', 'empresa1@sbs.com', '$2a$10$bbxuAS5vRkmaEPkG5DHiw.9.ttIT6Ejzs10GGkseWmlzzX5D/9LuG', 1, NOW(), NOW());
INSERT INTO users (id, username, email, password, empresa_id, created_at, updated_at) VALUES (3, 'empresa2admin', 'empresa2@sbsd.com', '$2a$10$bbxuAS5vRkmaEPkG5DHiw.9.ttIT6Ejzs10GGkseWmlzzX5D/9LuG', 2, NOW(), NOW());
INSERT INTO users (id, username, email, password, empresa_id, created_at, updated_at) VALUES (4, 'empleado1', 'empleado1@sbs.com', '$2a$10$bbxuAS5vRkmaEPkG5DHiw.9.ttIT6Ejzs10GGkseWmlzzX5D/9LuG', 1, NOW(), NOW());
INSERT INTO users (id, username, email, password, empresa_id, created_at, updated_at) VALUES (5, 'empleado2', 'empleado2@sbsd.com', '$2a$10$bbxuAS5vRkmaEPkG5DHiw.9.ttIT6Ejzs10GGkseWmlzzX5D/9LuG', 2, NOW(), NOW());
INSERT INTO users (id, username, email, password, empresa_id, created_at, updated_at) VALUES (6, 'userlibre', 'libre@example.com', '$2a$10$bbxuAS5vRkmaEPkG5DHiw.9.ttIT6Ejzs10GGkseWmlzzX5D/9LuG', null, NOW(), NOW());

-- Roles de los usuarios
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (4, 3);
INSERT INTO user_roles (user_id, role_id) VALUES (5, 3);
INSERT INTO user_roles (user_id, role_id) VALUES (6, 4);

-- Categorías para SBS Ingeniería
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Electrónica 1-1', 1, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Herramientas 1-2', 1, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Material Eléctrico 1-3', 1, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Instrumentación 1-4', 1, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Automatización 1-5', 1, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Fontanería 1-6', 1, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Climatización 1-7', 1, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Construcción 1-8', 1, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Seguridad 1-9', 1, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Suministros Generales 1-10', 1, NOW());

-- Categorías para SBS Digital
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Electrónica 2-1', 2, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Herramientas 2-2', 2, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Material Eléctrico 2-3', 2, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Instrumentación 2-4', 2, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Automatización 2-5', 2, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Fontanería 2-6', 2, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Climatización 2-7', 2, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Construcción 2-8', 2, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Seguridad 2-9', 2, NOW());
INSERT INTO categoria (nombre, empresa_id, fecha_creacion) VALUES ('Suministros Generales 2-10', 2, NOW());


-- Proveedores para SBS Ingeniería
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Proveedora Andina 1-1', 'proveedoraandina1-1@example.com', '612345678', 1, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('TecnoDistribuidora 1-2', 'tecnodistribuidora1-2@example.com', '623456789', 1, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('ElectroSuministros 1-3', 'electrosuministros1-3@example.com', '634567890', 1, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Suministros del Sur 1-4', 'suministrosdelsur1-4@example.com', '645678901', 1, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Equipamientos SBS 1-5', 'equipamientossbs1-5@example.com', '656789012', 1, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Proveedores Integrales 1-6', 'proveedoresintegrales1-6@example.com', '667890123', 1, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Logística Central 1-7', 'logisticacentral1-7@example.com', '678901234', 1, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Materiales Industriales 1-8', 'materialesindustriales1-8@example.com', '689012345', 1, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Red de Proveedores 1-9', 'reddeproveedores1-9@example.com', '690123456', 1, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Almacenes Técnicos 1-10', 'almacenestecnicos1-10@example.com', '601234567', 1, NOW());

-- Proveedores para SBS Digital
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Proveedora Andina 2-1', 'proveedoraandina2-1@example.com', '612345679', 2, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('TecnoDistribuidora 2-2', 'tecnodistribuidora2-2@example.com', '623456780', 2, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('ElectroSuministros 2-3', 'electrosuministros2-3@example.com', '634567891', 2, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Suministros del Sur 2-4', 'suministrosdelsur2-4@example.com', '645678902', 2, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Equipamientos SBS 2-5', 'equipamientossbs2-5@example.com', '656789013', 2, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Proveedores Integrales 2-6', 'proveedoresintegrales2-6@example.com', '667890124', 2, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Logística Central 2-7', 'logisticacentral2-7@example.com', '678901235', 2, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Materiales Industriales 2-8', 'materialesindustriales2-8@example.com', '689012346', 2, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Red de Proveedores 2-9', 'reddeproveedores2-9@example.com', '690123457', 2, NOW());
INSERT INTO proveedor (nombre, email, telefono, empresa_id, fecha_creacion) VALUES ('Almacenes Técnicos 2-10', 'almacenestecnicos2-10@example.com', '601234568', 2, NOW());


-- Productos para SBS Ingeniería (empresa_id = 1)
INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id) VALUES ('Ratón inalámbrico', 19.99, 10, true, NOW(), 1, 1, 1, 2);
INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id) VALUES ('Teclado mecánico', 45.00, 5, true, NOW(), 1, 2, 2, 2);
INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id) VALUES ('Pantalla LED 24"', 110.00, 8, true, NOW(), 1, 3, 3, 2);
INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id) VALUES ('Cables HDMI', 5.50, 25, true, NOW(), 1, 4, 4, 2);
-- Productos para SBS Digital (empresa_id = 2)
INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id) VALUES ('Escoba industrial', 12.00, 15, true, NOW(), 2, 5, 5, 3);
INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id) VALUES ('Detergente multiuso', 8.75, 20, true, NOW(), 2, 6, 6, 3);
INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id) VALUES ('Guantes de limpieza', 2.50, 50, true, NOW(), 2, 7, 7, 3);
INSERT INTO producto (nombre, precio, cantidad, uso_interno, fecha_ingreso, empresa_id, categoria_id, proveedor_id, usuario_id) VALUES ('Trapeador absorbente', 7.00, 10, true, NOW(), 2, 8, 8, 3);


-- Movimientos
INSERT INTO movimiento_producto (producto_id, usuario_id, cantidad, tipo, fecha, empresa_id) VALUES (1, 2, 1, 'ENTRADA', NOW(), 1);

-- Almacenes para SBS Ingeniería
INSERT INTO almacen (nombre, direccion, empresa_id) VALUES ('Almacén Central 1', 'Calle Inventada 123', 1);
INSERT INTO almacen (nombre, direccion, empresa_id) VALUES ('Almacén Norte 1', 'Av. Técnica 45', 1);

-- Almacenes para SBS Digital
INSERT INTO almacen (nombre, direccion, empresa_id) VALUES ('Almacén Central 2', 'Calle Digital 88', 2);

-- Plantas
INSERT INTO planta (nombre, numero, almacen_id) VALUES ('Planta Baja', 0, 1);
INSERT INTO planta (nombre, numero, almacen_id) VALUES ('Planta 1', 1, 1);
INSERT INTO planta (nombre, numero, almacen_id) VALUES ('Planta Baja', 0, 2);
INSERT INTO planta (nombre, numero, almacen_id) VALUES ('Planta 1', 1, 2);
INSERT INTO planta (nombre, numero, almacen_id) VALUES ('Planta Baja', 0, 3);

-- Estanterías
INSERT INTO estanteria (codigo, planta_id) VALUES ('F1-E1', 1);
INSERT INTO estanteria (codigo, planta_id) VALUES ('F2-E2', 2);
INSERT INTO estanteria (codigo, planta_id) VALUES ('F1-E1', 3);
INSERT INTO estanteria (codigo, planta_id) VALUES ('F2-E2', 4);
INSERT INTO estanteria (codigo, planta_id) VALUES ('F1-E1', 5);

