-- Roles
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_ADMIN_EMPRESA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_EMPLEADO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Usuario admin (sin foto_perfil)
INSERT INTO users (username, email, password, created_at, updated_at) VALUES ('admin', 'admin@email.com', '$2a$10$H/xUHT7VQeANAJ5ZjiR1KuoQCjMTE4.qnIpTH0i/rUmRTKtduNxVW', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Asignar rol ROLE_ADMIN al usuario admin
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
