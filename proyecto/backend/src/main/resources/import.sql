INSERT INTO roles (name) VALUES ('ROLE_EMPLEADO');
INSERT INTO roles (name) VALUES ('ROLE_SUPER_ADMIN');

INSERT INTO users (username, password, email, nif, nombre, apellido1, apellido2, telefono) VALUES('admin', '$2a$10$xu5KDwUJcHHlJFR3PMFxlORNFFpGnozs3Q9sByBv5P5xayT/z4ax2', 'admin@gmail.com','12345678A', 'Admin', 'Apellido1', 'Apellido2', 600123456);

INSERT INTO user_roles (user_id,role_id) VALUES (1,1);
INSERT INTO user_roles (user_id,role_id) VALUES (1,2);
