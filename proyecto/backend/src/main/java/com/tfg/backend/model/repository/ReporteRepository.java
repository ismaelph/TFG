package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    Reporte findById(long id);
    List<Reporte> findByUsuarioId(long usuarioId);
    List<Reporte> findByNombreReporte(String nombre);
}
