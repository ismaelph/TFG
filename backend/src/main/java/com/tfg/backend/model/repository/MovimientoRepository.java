package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    Optional<Movimiento> findById(Long id);
    List<Movimiento> findByProductoId(Long productoId);
    List<Movimiento> findByUsuarioId(Long usuarioId);


    @Query("SELECT m FROM Movimiento m WHERE m.producto.empresa.id = :empresaId")
    List<Movimiento> buscarPorEmpresa(@Param("empresaId") Long empresaId);

}
