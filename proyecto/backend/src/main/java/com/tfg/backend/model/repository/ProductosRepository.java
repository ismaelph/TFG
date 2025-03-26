package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductosRepository extends JpaRepository<Producto, Long> {
    Producto findById(long id);
    Producto findByNombre(String nombre);
}
