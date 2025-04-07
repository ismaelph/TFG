package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findById(Long id);
    Optional<Producto> findByNombre(String nombre);
    List<Producto> findByCategoriaNombre(String nombreCategoria);
    List<Producto> findByEmpresaId(Long empresaId);
}
