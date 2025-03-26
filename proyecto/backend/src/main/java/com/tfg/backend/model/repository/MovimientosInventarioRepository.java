package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovimientosInventarioRepository extends JpaRepository<MovimientoInventario, Long> {
    MovimientoInventario findById(long id);
    List<MovimientoInventario> findByProductoId(long productoId);
    List<MovimientoInventario> findByUsuarioId(long usuarioId);
    // 🔹 Buscar movimientos por tipo
    @Query("SELECT m FROM MovimientoInventario m WHERE m.tipoMovimiento = :tipo")
    List<MovimientoInventario> findByTipoMovimiento(@Param("tipo") String tipo);
}
