package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.ProductoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoUsuarioRepository extends JpaRepository<ProductoUsuario, Long> {
    ProductoUsuario findById(long id);
    List<ProductoUsuario> findByProductoId(long productoId);
    List<ProductoUsuario> findByUsuarioId(long usuarioId);
}
