-- Roles
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_ADMIN_EMPRESA', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_EMPLEADO', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO roles (name, created_at, updated_at) VALUES ('ROLE_USER', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

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