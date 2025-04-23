-- ROLES

INSERT INTO roles (name) VALUES ('ROLE_EMPLEADO');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN');
INSERT INTO roles (name) VALUES ('ROLE_ADMIN_EMPRESA');
INSERT INTO roles (name) VALUES ('ROLE_USER');

-- USUARIOS
INSERT INTO users (username, password, email) VALUES ('admin','$2a$10$xu5KDwUJcHHlJFR3PMFxlORNFFpGnozs3Q9sByBv5P5xayT/z4ax2', 'admin@gmail.com');

INSERT INTO user_roles (user_id,role_id) VALUES (1,1);
INSERT INTO user_roles (user_id,role_id) VALUES (1,2);

