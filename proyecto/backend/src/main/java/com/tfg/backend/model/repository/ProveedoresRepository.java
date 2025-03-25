package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedoresRepository extends JpaRepository<Proveedor, Long> {
    Proveedor findByEmail(String email);
    Proveedor findById(long id);
}
