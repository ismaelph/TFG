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

INSERT INTO empresas (nombre, password, creador_id) VALUES ('Empresa A', 'pass1234', 1);
INSERT INTO empresas (nombre, password, creador_id) VALUES ('Empresa B', 'pass1234', 2);
INSERT INTO empresas (nombre, password, creador_id) VALUES ('Empresa C', 'pass1234', 3);
INSERT INTO empresas (nombre, password, creador_id) VALUES ('Empresa D', 'pass1234', 4);
INSERT INTO empresas (nombre, password, creador_id) VALUES ('Empresa E', 'pass1234', 5);
INSERT INTO empresas (nombre, password, creador_id) VALUES ('Empresa F', 'pass1234', 1);
INSERT INTO empresas (nombre, password, creador_id) VALUES ('Empresa G', 'pass1234', 2);
INSERT INTO empresas (nombre, password, creador_id) VALUES ('Empresa H', 'pass1234', 3);
INSERT INTO empresas (nombre, password, creador_id) VALUES ('Empresa I', 'pass1234', 4);
INSERT INTO empresas (nombre, password, creador_id) VALUES ('Empresa J', 'pass1234', 5);
INSERT INTO empresas (nombre, password, creador_id) VALUES ('Empresa K', 'pass1234', 1);
INSERT INTO empresas (nombre, password, creador_id) VALUES ('Empresa L', 'pass1234', 2);
INSERT INTO empresas (nombre, password, creador_id) VALUES ('Empresa M', 'pass1234', 3);
INSERT INTO empresas (nombre, password, creador_id) VALUES ('Empresa N', 'pass1234', 4);
INSERT INTO empresas (nombre, password, creador_id) VALUES ('Empresa O', 'pass1234', 5);
