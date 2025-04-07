package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    Optional<Reporte> findById(Long id);
    List<Reporte> findByUsuarioId(Long usuarioId);
}
