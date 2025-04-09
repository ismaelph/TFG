package com.tfg.backend.service;

import com.tfg.backend.model.entity.Reporte;

import java.util.List;

public interface ReporteService {
    Reporte save(Reporte reporte);
    void delete(long id);

    Reporte findById(long id);
    List<Reporte> findByUsuarioId(Long usuarioId);
}
