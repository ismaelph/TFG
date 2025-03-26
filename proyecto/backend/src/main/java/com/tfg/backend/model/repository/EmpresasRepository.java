package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresasRepository extends JpaRepository<Empresa, Long> {
    Empresa findById(long id);
    Empresa findByNombre(String nombre);
}
