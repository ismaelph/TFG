package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriasRepository extends JpaRepository<Categoria, Long> {
    Categoria findById(long id);
    Categoria findByNombre(String nombre);
}
