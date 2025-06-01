
# 🧩 Invecta - Gestión de Inventario Empresarial

**Invecta** es una plataforma web de gestión de inventario orientada a empresas, desarrollada como proyecto final. Permite a los administradores controlar productos, organizar almacenes, gestionar proveedores y categorías, y a los empleados realizar solicitudes de productos. Está construida con Angular + TailwindCSS en el frontend y Spring Boot + MySQL en el backend.

---

## 🧪 Usuarios de prueba

> Todos los usuarios usan la contraseña `castelar`.

| Usuario              | Rol               | Email                              |
|---------------------|-------------------|------------------------------------|
| Admin Global        | ROLE_ADMIN        | admin@example.com                  |
| Admin Empresa 1     | ROLE_ADMIN_EMPRESA| empresa1@sbs.com                   |
| Admin Empresa 2     | ROLE_ADMIN_EMPRESA| pavonhueteismael@gmail.com         |
| Empleado Empresa 1  | ROLE_EMPLEADO     | pavonhueteismael@proton.me         |
| Empleado Empresa 2  | ROLE_EMPLEADO     | rajanteflorencio@gmail.com         |
| Usuario Libre       | ROLE_USER         | libre@example.com                  |

---

## 🧰 Tecnologías utilizadas

### Backend
- Java 17 + Spring Boot 3
- Spring Security con JWT
- Spring Data JPA (Hibernate)
- JavaMailSender para correos
- MySQL (desarrollo con H2 opcional)
- Gradle como sistema de construcción

### Frontend
- Angular 14.2.6
- TailwindCSS (con paleta personalizada)
- Bootstrap Icons + SweetAlert2
- Angular Interceptors para JWT y errores

---

## ⚙️ Paleta de colores (Tailwind)

```js
primary: '#1E1E1E',
secondary: '#3A5A74',
accent: '#6BB2A7',
accent-dark: '#417B73',
secondary-light: '#6B7280',
background: '#F5F5F5',
```

---

## 📦 Funcionalidades destacadas

- Gestión de empresas, usuarios, productos, almacenes.
- Estructura física real: almacenes → plantas → estanterías.
- Solicitudes normales y personalizadas de productos.
- Sistema de notificaciones por solicitudes.
- Correos automáticos para avisos de stock.
- Control de roles y permisos.
- Exportación de inventario y movimientos (pendiente).
- Historial de análisis de riesgo PCI por usuario.

---

## 🐳 Despliegue en producción

La aplicación está desplegada en **Azure** mediante contenedores Docker:

- Un contenedor para el backend (Spring Boot con Tomcat)
- Un contenedor para el frontend Angular
- Un contenedor con MySQL como base de datos
- Proxy inverso con Caddy (puertos 80 y 443)
- DNS pública: [invecta.ddns.net](http://invecta.ddns.net) (mediante No-IP)

---

## 🔐 Seguridad

- Login con JWT (token seguro)
- Guardas e interceptores en Angular
- Protección de endpoints con `@PreAuthorize`
- Cambios de contraseña y recuperación por correo
- Roles definidos: ADMIN, ADMIN_EMPRESA, EMPLEADO, USER

---

## ⚡ Instalación local (dev)

### Backend

```bash
./gradlew bootRun
```

Por defecto usa H2, puedes cambiar a MySQL en `application.yml`.

### Frontend

```bash
npm install
ng serve
```

---

## 📂 Estructura del proyecto

```
backend/
├── controller/
├── model/
├── service/
├── config/
└── repository/

frontend/
├── app/
│   ├── core/
│   ├── shared/
│   ├── modules/
│   └── auth/
└── assets/
```

---

## 🧑 Autor

Desarrollado por **Ismael Pavón Huete** – Proyecto para [iescastelar](https://iescastelar.educarex.es/)

Contacto: pavonhueteismael@gmail.com
