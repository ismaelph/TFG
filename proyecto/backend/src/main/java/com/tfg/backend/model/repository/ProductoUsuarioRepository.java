package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.ProductoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoUsuarioRepository extends JpaRepository<ProductoUsuario, Long> {
    ProductoUsuario findById(long id);
    @Query("SELECT pu FROM ProductoUsuario pu WHERE pu.producto.id = :productoId")
    ProductoUsuario findByProducto(@Param("productoId") long productoId);

    @Query("SELECT pu FROM ProductoUsuario pu WHERE pu.usuario.id = :usuarioId")
    ProductoUsuario findByUsuario(@Param("usuarioId") long usuarioId);}
