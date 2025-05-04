-- ROLES

INSERT INTO roles (name) VALUES ('ROLE_EMPLEADO');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN_EMPRESA');
INSERT INTO roles (name) VALUES ('ROLE_USER');

-- USUARIOS
INSERT INTO users (username, password, email) VALUES ('admin','$2a$10$xu5KDwUJcHHlJFR3PMFxlORNFFpGnozs3Q9sByBv5P5xayT/z4ax2', 'admin@gmail.com');

INSERT INTO user_roles (user_id,role_id) VALUES (1,1);
INSERT INTO user_roles (user_id,role_id) VALUES (1,2);

INSERT INTO users (username, email, password) VALUES ('user1', 'user1@example.com', '$2a$10$xu5KDwUJcHHlJFR3PMFxlORNFFpGnozs3Q9sByBv5P5xayT/z4ax2');
INSERT INTO users (username, email, password) VALUES ('user2', 'user2@example.com', '$2a$10$xu5KDwUJcHHlJFR3PMFxlORNFFpGnozs3Q9sByBv5P5xayT/z4ax2');
INSERT INTO users (username, email, password) VALUES ('user3', 'user3@example.com', '$2a$10$xu5KDwUJcHHlJFR3PMFxlORNFFpGnozs3Q9sByBv5P5xayT/z4ax2');
INSERT INTO users (username, email, password) VALUES ('user4', 'user4@example.com', '$2a$10$xu5KDwUJcHHlJFR3PMFxlORNFFpGnozs3Q9sByBv5P5xayT/z4ax2');
INSERT INTO users (username, email, password) VALUES ('user5', 'user5@example.com', '$2a$10$xu5KDwUJcHHlJFR3PMFxlORNFFpGnozs3Q9sByBv5P5xayT/z4ax2');


-- EMPRESAS
INSERT INTO empresas (id, nombre, password) VALUES (1, 'Empresa A', 'pass1234');
INSERT INTO empresas (id, nombre, password) VALUES (2, 'Empresa B', 'pass1234');
INSERT INTO empresas (id, nombre, password) VALUES (3, 'Empresa C', 'pass1234');

-- RELACIÓN EMPRESAS - ADMINISTRADORES
INSERT INTO empresa_admins (empresa_id, user_id) VALUES (1, 1); -- Admin de Empresa A
INSERT INTO empresa_admins (empresa_id, user_id) VALUES (2, 2); -- Admin de Empresa B
INSERT INTO empresa_admins (empresa_id, user_id) VALUES (3, 3); -- Admin de Empresa C
