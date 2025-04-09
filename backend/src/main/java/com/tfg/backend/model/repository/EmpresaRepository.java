package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Empresa findById(long id);
    Empresa findByNombre(String nombre);
}
