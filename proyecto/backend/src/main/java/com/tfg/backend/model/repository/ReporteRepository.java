package com.tfg.backend.model.repository;

import com.tfg.backend.model.entity.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    Reporte findById(long id);
    List<Reporte> findByUsuarioId(long usuarioId);
    List<Reporte> findByNombreReporte(String nombre);

    @Query(value = "SELECT * FROM reporte WHERE fecha_generacion >= CURRENT_DATE - INTERVAL '30 days'", nativeQuery = true)
    List<Reporte> findReportesUltimoMes();

}
