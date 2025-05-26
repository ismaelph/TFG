package com.tfg.backend.service;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.dto.SolicitudPersonalizadaDto;
import com.tfg.backend.model.entity.SolicitudPersonalizada;

import java.util.List;

public interface SolicitudPersonalizadaService {
    SolicitudPersonalizada save(SolicitudPersonalizada solicitud);
    SolicitudPersonalizada findById(Long id);
    List<SolicitudPersonalizada> findAll();
    List<SolicitudPersonalizada> findByUsuarioId(Long usuarioId);
    SolicitudPersonalizada updateEstado(SolicitudPersonalizada solicitud, Long id);
    void delete(Long id);
    void crearSolicitud(SolicitudPersonalizadaDto dto, User usuario);

}
