package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    Optional<Proveedor> findById(Long id);
    Optional<Proveedor> findByNombre(String nombre);
}
