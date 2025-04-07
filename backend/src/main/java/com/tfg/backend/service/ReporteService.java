package com.tfg.backend.service;

import com.tfg.backend.model.entity.Reporte;

import java.util.List;

public interface ReporteService {
    Reporte save(Reporte reporte);
    void delete(Long id);

    Reporte findById(Long id);
    List<Reporte> findByUsuarioId(Long usuarioId);
}
