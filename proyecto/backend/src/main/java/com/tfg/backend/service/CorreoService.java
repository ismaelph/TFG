package com.tfg.backend.service;

import com.tfg.backend.model.entity.SolicitudMovimiento;
import com.tfg.backend.model.entity.SolicitudPersonalizada;

public interface CorreoService {
    void enviarCorreo(String para, String asunto, String contenido);
    void enviarCorreoNuevaSolicitudMovimiento(SolicitudMovimiento solicitud);
    void enviarCorreoNuevaSolicitudPersonalizada(SolicitudPersonalizada solicitud);
}

