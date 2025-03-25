package com.tfg.backend.auth.models;

public enum RoleEnum {
  ROLE_SUPER_ADMIN,  // Admin general del sistema
  ROLE_ADMIN_EMPRESA,  // Admin de una empresa específica
  ROLE_EMPLEADO,  // Empleado que usa el sistema
  ROLE_USER  // Usuario estándar (si aplica)
}
