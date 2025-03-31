-- ROLES

INSERT INTO roles (name) VALUES ('ROLE_EMPLEADO');
INSERT INTO roles (name) VALUES ('ROLE_SUPER_ADMIN');

-- EMPRESAS

INSERT INTO empresas (nombre, cif, direccion, ciudad, pais, telefono, email, web) VALUES('Tech Solutions S.L.', 'B12345678', 'Calle Falsa 123', 'Madrid', 'España', '+34911223344', 'contacto@techsolutions.com', 'www.techsolutions.com'),('Global Trade Corp.', 'A98765432', 'Av. Comercial 456', 'Barcelona', 'España', '+34915556677', 'info@globaltrade.com', 'www.globaltrade.com'),('Innovatech Group', 'C56789012', 'Plaza Mayor 789', 'Valencia', 'España', '+34918889900', 'support@innovatech.com', 'www.innovatech.com'),('EcoFriendly Ltd.', 'D34567890', 'Carrera Verde 101', 'Sevilla', 'España', '+34914445566', 'eco@ecofriendly.com', 'www.ecofriendly.com'),('Logistics Express', 'E21098765', 'Paseo del Puerto 202', 'Bilbao', 'España', '+34912223344', 'sales@logisticsexpress.com', 'www.logisticsexpress.com');

-- USUARIOS
INSERT INTO users (username, password, email, nif, nombre, apellido1, apellido2, telefono) VALUES('admin', '$2a$10$xu5KDwUJcHHlJFR3PMFxlORNFFpGnozs3Q9sByBv5P5xayT/z4ax2', 'admin@gmail.com','12345678A', 'Admin', 'Apellido1', 'Apellido2', 600123456);

INSERT INTO users (username, password, email, nif, nombre, apellido1, apellido2, telefono, direccion, empresa_id) VALUES ('jgarcia', '$2a$10$xu5KDwUJcHHlJFR3PMFxlORNFFpGnozs3Q9sByBv5P5xayT', 'jgarcia@email.com', '87654321B', 'Juan', 'García', 'Fernández', 610987654, 'Calle Mayor 10, Madrid', 1), ('mmartinez', '$2a$10$xu5KDwUJcHHlJFR3PMFxlORNFFpGnozs3Q9sByBv5P5xayT', 'mmartinez@email.com', '11223344C', 'María', 'Martínez', 'López', 620456789, 'Avenida Central 23, Barcelona', 2);

INSERT INTO user_roles (user_id,role_id) VALUES (1,1);
INSERT INTO user_roles (user_id,role_id) VALUES (1,2);

-- CATEGORIAS

INSERT INTO categorias (nombre, descripcion) VALUES ('Electrónica', 'Productos electrónicos y gadgets'),('Ropa', 'Vestimenta para todas las edades'),('Hogar', 'Artículos para el hogar y decoración'),('Deportes', 'Equipamiento y accesorios deportivos'),('Libros', 'Libros de diferentes géneros y autores');

-- PROVEEDORES

INSERT INTO proveedores (nombre, email, telefono, direccion) VALUES('Tech Supplies S.L.', 'contacto@techsupplies.com', '+34911223344', 'Calle Tecnológica 123, Madrid, España'),('Distribuciones Globales', 'ventas@distribucionesglobales.com', '+34915556677', 'Av. Comercial 456, Barcelona, España'),('Innovatech Proveedores', 'info@innovatechproveedores.com', '+34918889900', 'Plaza Mayor 789, Valencia, España'),('EcoDistribución', 'eco@ecodistribucion.com', '+34914445566', 'Carrera Verde 101, Sevilla, España'),('Logística y Suministros', 'logistica@suministros.com', '+34912223344', 'Paseo del Puerto 202, Bilbao, España');

-- PRODUCTOS

INSERT INTO productos (nombre, descripcion, precio, cantidad, uso_Interno, categoria_id, proveedor_id, administrador_id) VALUES('Laptop HP EliteBook', 'Portátil de alto rendimiento para empresas', 1200.99, 10, false, 1, 1, 1),('Mouse Inalámbrico Logitech', 'Mouse ergonómico con conexión Bluetooth',  29.99, 50, false, 1, 2, 1),('Silla Ergonómica', 'Silla de oficina con ajuste lumbar', 199.99, 20, true, 3, 3, 2),('Cámara de Seguridad', 'Cámara IP con visión nocturna y detección de movimiento',  89.50, 15, false, 4, 4, 2),('Libro "Gestión Empresarial"', 'Guía práctica sobre gestión empresarial',  24.99, 30, false, 5, 5, 3);

