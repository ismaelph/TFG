package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    Optional<Proveedor> findById(Long id);

    List<Proveedor> findByEmpresa(Empresa empresa);
}
