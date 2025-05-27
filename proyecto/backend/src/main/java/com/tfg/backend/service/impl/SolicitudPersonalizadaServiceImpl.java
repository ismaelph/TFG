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
    private SolicitudPersonalizadaRepository solicitudPersonalizadaRepository;

    @Autowired
    private CorreoService correoService;

    @Override
    public SolicitudPersonalizada save(SolicitudPersonalizada solicitud) {
        if (solicitud.getUsuario() != null && solicitud.getUsuario().getId() != null) {
            User usuario = userRepository.findById(solicitud.getUsuario().getId()).orElse(null);
            solicitud.setUsuario(usuario);
        }
        return solicitudRepository.save(solicitud);
    }

    @Override
    public SolicitudPersonalizada findById(Long id) {
        return solicitudRepository.findById(id).orElse(null);
    }

    @Override
    public List<SolicitudPersonalizada> findAll() {
        return solicitudRepository.findAll();
    }

    @Override
    public List<SolicitudPersonalizada> findByUsuarioId(Long usuarioId) {
        User usuario = userRepository.findById(usuarioId).orElse(null);
        if (usuario != null) {
            return solicitudRepository.findByUsuario(usuario);
        }
        return List.of();
    }

    @Override
    public SolicitudPersonalizada updateEstado(SolicitudPersonalizada solicitud, Long id) {
        SolicitudPersonalizada actual = findById(id);
        if (actual != null) {
            actual.setEstado(solicitud.getEstado());
            actual.setRespuestaAdmin(solicitud.getRespuestaAdmin());
            actual.setFechaResolucion(Instant.now());
            return solicitudRepository.save(actual);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        solicitudRepository.deleteById(id);
    }

    @Override
    public void crearSolicitud(SolicitudPersonalizadaDto dto, User usuario) {
        try {
            System.out.println("📥 Creando nueva solicitud personalizada...");
            System.out.println("🔎 DTO recibido: nombre = " + dto.getNombreProductoSugerido()
                    + ", cantidad = " + dto.getCantidadDeseada()
                    + ", motivo = " + dto.getDescripcion());

            // Convertir el DTO en entidad
            SolicitudPersonalizada solicitud = dto.to();

            // Validar empresa del usuario
            if (usuario.getEmpresa() == null) {
                System.err.println("🚫 El usuario no está asociado a ninguna empresa.");
                throw new RuntimeException("El usuario debe estar vinculado a una empresa para solicitar.");
            }

            // Asignar campos
            solicitud.setEstado(EstadoSolicitud.PENDIENTE);
            solicitud.setFechaSolicitud(Instant.now());
            solicitud.setUsuario(usuario);

            // Log detallado
            System.out.println("📦 Nombre sugerido: " + solicitud.getNombreProductoSugerido());
            System.out.println("🔢 Cantidad: " + solicitud.getCantidadDeseada());
            System.out.println("📝 Motivo: " + solicitud.getDescripcion());
            System.out.println("🏢 Empresa: " + usuario.getEmpresa().getNombre());
            System.out.println("👤 Usuario: " + usuario.getUsername());

            // Guardar en base de datos
            solicitudPersonalizadaRepository.save(solicitud);
            System.out.println("✅ Solicitud personalizada guardada correctamente.");

            // Enviar correo
            correoService.enviarCorreoNuevaSolicitudPersonalizada(solicitud);
            System.out.println("📧 Correo enviado al administrador de empresa.");

        } catch (Exception e) {
            System.err.println("❌ Error al crear solicitud personalizada:");
            e.printStackTrace();
            throw new RuntimeException("Error al procesar la solicitud personalizada");
        }
    }

    @Override
    public List<SolicitudPersonalizada> findByEmpresaAndLeidaFalse(Empresa empresa) {
        return solicitudPersonalizadaRepository.findByUsuario_EmpresaAndLeidaFalse(empresa);
    }



}
