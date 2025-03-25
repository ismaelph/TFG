package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimientosInventarioRepository extends JpaRepository<MovimientoInventario, Long> {
    MovimientoInventario findById(long id);
    List<MovimientoInventario> findByProductoId(long productoId);
    List<MovimientoInventario> findByUsuarioId(long usuarioId);
}
