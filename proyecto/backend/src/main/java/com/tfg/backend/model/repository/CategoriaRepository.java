package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Categoria;
import com.tfg.backend.model.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findById(Long id);

    List<Categoria> findByEmpresa(Empresa empresa);

    void deleteAllByEmpresa(Empresa empresa);

}
