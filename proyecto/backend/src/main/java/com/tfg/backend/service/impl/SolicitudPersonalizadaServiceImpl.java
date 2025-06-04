package com.tfg.backend.service.impl;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.repository.UserRepository;
import com.tfg.backend.model.dto.SolicitudPersonalizadaDto;
import com.tfg.backend.model.entity.Empresa;
import com.tfg.backend.model.entity.SolicitudPersonalizada;
import com.tfg.backend.model.enums.EstadoSolicitud;
import com.tfg.backend.model.repository.SolicitudPersonalizadaRepository;
import com.tfg.backend.service.CorreoService;
import com.tfg.backend.service.SolicitudPersonalizadaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class SolicitudPersonalizadaServiceImpl implements SolicitudPersonalizadaService {

    @Autowired
    private SolicitudPersonalizadaRepository solicitudRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CorreoService correoService;

    @Override
    public SolicitudPersonalizada save(SolicitudPersonalizada solicitud) {
        System.out.println("💾 Guardando solicitud personalizada");

        if (solicitud.getUsuario() != null && solicitud.getUsuario().getId() != null) {
            User usuario = userRepository.findById(solicitud.getUsuario().getId()).orElse(null);
            solicitud.setUsuario(usuario);
        }

        return solicitudRepository.save(solicitud);
    }

    @Override
    public SolicitudPersonalizada findById(Long id) {
        System.out.println("🔍 Buscando solicitud personalizada ID: " + id);
        return solicitudRepository.findById(id).orElse(null);
    }

    @Override
    public List<SolicitudPersonalizada> findAll() {
        System.out.println("📂 Listando todas las solicitudes personalizadas");
        return solicitudRepository.findAll();
    }

    @Override
    public List<SolicitudPersonalizada> findByUsuarioId(Long usuarioId) {
        System.out.println("🔍 Buscando solicitudes personalizadas del usuario ID: " + usuarioId);
        User usuario = userRepository.findById(usuarioId).orElse(null);
        if (usuario != null) {
            return solicitudRepository.findByUsuario(usuario);
        }
        System.err.println("❌ Usuario no encontrado con ID: " + usuarioId);
        return List.of();
    }

    @Override
    public SolicitudPersonalizada updateEstado(SolicitudPersonalizada solicitud, Long id) {
        System.out.println("🔄 Actualizando estado de solicitud personalizada ID: " + id);
        SolicitudPersonalizada actual = findById(id);
        if (actual != null) {
            actual.setEstado(solicitud.getEstado());
            actual.setRespuestaAdmin(solicitud.getRespuestaAdmin());
            actual.setFechaResolucion(Instant.now());
            return solicitudRepository.save(actual);
        }
        System.err.println("❌ Solicitud personalizada no encontrada");
        return null;
    }

    @Override
    public void delete(Long id) {
        System.out.println("🗑 Eliminando solicitud personalizada ID: " + id);
        solicitudRepository.deleteById(id);
    }

    @Override
    public void crearSolicitud(SolicitudPersonalizadaDto dto, User usuario) {
        try {
            System.out.println("📥 Creando nueva solicitud personalizada...");
            System.out.println("🔎 DTO recibido: nombre = " + dto.getNombreProductoSugerido()
                    + ", cantidad = " + dto.getCantidadDeseada()
                    + ", motivo = " + dto.getDescripcion());

            SolicitudPersonalizada solicitud = dto.to();

            if (usuario.getEmpresa() == null) {
                System.err.println("🚫 El usuario no está asociado a ninguna empresa.");
                throw new RuntimeException("El usuario debe estar vinculado a una empresa para solicitar.");
            }

            solicitud.setEstado(EstadoSolicitud.PENDIENTE);
            solicitud.setFechaSolicitud(Instant.now());
            solicitud.setUsuario(usuario);

            System.out.println("📦 Nombre sugerido: " + solicitud.getNombreProductoSugerido());
            System.out.println("🔢 Cantidad: " + solicitud.getCantidadDeseada());
            System.out.println("📝 Motivo: " + solicitud.getDescripcion());
            System.out.println("🏢 Empresa: " + usuario.getEmpresa().getNombre());
            System.out.println("👤 Usuario: " + usuario.getUsername());

            solicitudRepository.save(solicitud);
            System.out.println("✅ Solicitud personalizada guardada correctamente");

            correoService.enviarCorreoNuevaSolicitudPersonalizada(solicitud);
            System.out.println("📧 Correo enviado al administrador");

        } catch (Exception e) {
            System.err.println("❌ Error al crear solicitud personalizada:");
            e.printStackTrace();
            throw new RuntimeException("Error al procesar la solicitud personalizada");
        }
    }

    @Override
    public List<SolicitudPersonalizada> findByEmpresaAndLeidaFalse(Empresa empresa) {
        System.out.println("🔍 Buscando solicitudes NO leídas para empresa: " + empresa.getNombre());
        return solicitudRepository.findByUsuario_EmpresaAndLeidaFalse(empresa);
    }

    @Override
    public List<SolicitudPersonalizada> findByEmpresa(Empresa empresa) {
        System.out.println("🔍 Buscando todas las solicitudes personalizadas para empresa: " + empresa.getNombre());
        return solicitudRepository.findByUsuario_Empresa(empresa);
    }

    @Override
    public SolicitudPersonalizada resolverSolicitud(Long solicitudId, boolean aceptar, String respuestaAdmin) {
        System.out.println("🟢 Resolviendo solicitud personalizada ID: " + solicitudId + " | Aceptar: " + aceptar);
        System.out.println("📩 Respuesta del admin: " + respuestaAdmin);

        SolicitudPersonalizada solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("❌ Solicitud no encontrada"));

        if (solicitud.getEstado() != EstadoSolicitud.PENDIENTE) {
            System.out.println("⚠️ Solicitud ya procesada con estado: " + solicitud.getEstado());
            throw new IllegalStateException("La solicitud ya ha sido procesada.");
        }

        if (!aceptar) {
            solicitud.setEstado(EstadoSolicitud.RECHAZADA);
            System.out.println("🔴 Solicitud rechazada.");
        } else {
            solicitud.setEstado(EstadoSolicitud.EN_ESPERA_STOCK);
            System.out.println("⏳ Solicitud aceptada. Esperando a que se cree el producto.");
        }

        solicitud.setRespuestaAdmin(respuestaAdmin);
        solicitud.setFechaResolucion(Instant.now());
        solicitud.setLeida(true);

        return solicitudRepository.save(solicitud);
    }

}