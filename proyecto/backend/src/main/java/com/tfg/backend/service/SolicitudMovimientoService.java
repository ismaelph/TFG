package com.tfg.backend.service;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.dto.SolicitudMovimientoDto;
import com.tfg.backend.model.entity.SolicitudMovimiento;

import java.util.List;

public interface SolicitudMovimientoService {
    SolicitudMovimiento save(SolicitudMovimiento solicitud);
    SolicitudMovimiento findById(Long id);
    List<SolicitudMovimiento> findAll();
    List<SolicitudMovimiento> findByUsuarioId(Long usuarioId);
    SolicitudMovimiento updateEstado(SolicitudMovimiento solicitud, Long id);
    void delete(Long id);

    void crearSolicitud(SolicitudMovimientoDto dto, User usuario);
    void aprobarSolicitud(Long id, String respuestaAdmin);
    List<SolicitudMovimiento> findByEmpresa(com.tfg.backend.model.entity.Empresa empresa);
    SolicitudMovimiento resolverSolicitud(Long solicitudId, boolean aceptar, String respuestaAdmin);


}
