package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedoresRepository extends JpaRepository<Proveedor, Long> {
    Proveedor findByEmail(String email);
    Proveedor findById(long id);
    Proveedor findByNombre(String nombre);
}
