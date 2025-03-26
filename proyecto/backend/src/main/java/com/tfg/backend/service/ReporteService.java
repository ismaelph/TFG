package com.tfg.backend.service;

import com.tfg.backend.model.entity.Reporte;

import java.util.List;

public interface ReporteService {
    Reporte save(Reporte reporte);
    Reporte update(Reporte reporte, long id);
    void delete(long id);

    Reporte findById(long id);
    List<Reporte> findAll();
    List<Reporte> findReportesUltimoMes();
}
