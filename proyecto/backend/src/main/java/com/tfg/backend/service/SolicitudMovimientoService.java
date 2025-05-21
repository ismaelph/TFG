package com.tfg.backend.service;

import com.tfg.backend.model.entity.SolicitudMovimiento;

import java.util.List;

public interface SolicitudMovimientoService {
    SolicitudMovimiento save(SolicitudMovimiento solicitud);
    SolicitudMovimiento findById(Long id);
    List<SolicitudMovimiento> findAll();
    List<SolicitudMovimiento> findByUsuarioId(Long usuarioId);
    SolicitudMovimiento updateEstado(SolicitudMovimiento solicitud, Long id);
    void delete(Long id);
}
