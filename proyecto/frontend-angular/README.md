```bash
src/
│── app/
│   ├── core/          # Servicios generales (Auth, API, etc.)
│   │   ├── guards/    # Guards para autenticación y roles
│   │   ├── services/  # Solo los servicios generales
│   ├── shared/        # Componentes reutilizables globales (Navbar, Footer, Loader)
│   ├── modules/       
│   │   ├── auth/       # Autenticación (Login, Registro)
│   │   │   ├── pages/  # Vistas del módulo
│   │   │   ├── services/  # Servicios exclusivos de autenticación
│   │   │   ├── interfaces/ # Tipos de usuario, tokens, etc.
│   │   │   ├── auth-routing.module.ts  
│   │   ├── empresa/    # Gestión de empresa (Crear, Unirse)
│   │   │   ├── pages/   
│   │   │   ├── services/  
│   │   │   ├── interfaces/ 
│   │   │   ├── empresa-routing.module.ts  
│   │   ├── admin/      # Administración **general** de la plataforma
│   │   │   ├── pages/   
│   │   │   ├── services/  
│   │   │   ├── interfaces/ 
│   │   │   ├── admin-routing.module.ts  
│   │   ├── dashboard/  # **Panel del administrador de cada empresa**
│   │   │   ├── pages/   
│   │   │   ├── services/  
│   │   │   ├── interfaces/ 
│   │   │   ├── dashboard-routing.module.ts  
│   │   ├── inventario/ # **Manejo de inventario de cada empresa**
│   │   │   ├── pages/
│   │   │   ├── services/  
│   │   │   ├── interfaces/
│   │   │   ├── inventario-routing.module.ts  
│   │   ├── usuario/    # **Interfaz del usuario normal dentro de la empresa**
│   │   │   ├── pages/   
│   │   │   ├── services/  
│   │   │   ├── interfaces/ 
│   │   │   ├── usuario-routing.module.ts
│   │   ├── main/       # Página principal para visitar
│   │   │   ├── pages/   
│   │   │   │   ├── inicio/  # Componente de inicio
│   │   │   ├── main-routing.module.ts  
│   ├── app-routing.module.ts
```

npm install tailwindcss postcss autoprefixer
npm install typescript@4.8.4
