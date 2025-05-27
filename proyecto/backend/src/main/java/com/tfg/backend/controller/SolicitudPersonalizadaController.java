package com.tfg.backend.controller;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.services.UserDetailsImpl;
import com.tfg.backend.model.dto.SolicitudPersonalizadaDto;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.entity.SolicitudPersonalizada;
import com.tfg.backend.model.enums.EstadoSolicitud;
import com.tfg.backend.service.SolicitudPersonalizadaService;
import com.tfg.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/solicitudes/personalizada")
@Slf4j
public class SolicitudPersonalizadaController {

    @Autowired
    private SolicitudPersonalizadaService solicitudService;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<SolicitudPersonalizadaDto> listAll() {
        System.out.println("📂 Obteniendo todas las solicitudes personalizadas del sistema");
        return SolicitudPersonalizadaDto.from(solicitudService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        System.out.println("🔍 Buscando solicitud personalizada por ID: " + id);
        SolicitudPersonalizada solicitud = solicitudService.findById(id);
        if (solicitud != null) {
            return ResponseEntity.ok(SolicitudPersonalizadaDto.from(solicitud));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Solicitud no encontrada"));
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<SolicitudPersonalizadaDto> findByUsuario(@PathVariable Long usuarioId) {
        System.out.println("🔍 Buscando solicitudes del usuario ID: " + usuarioId);
        return SolicitudPersonalizadaDto.from(solicitudService.findByUsuarioId(usuarioId));
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody SolicitudPersonalizadaDto dto,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            Long userId = userDetails.getId();
            dto.setUsuarioId(userId);
            SolicitudPersonalizada nueva = dto.to();
            nueva.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoSolicitud.PENDIENTE);

            System.out.println("📝 Guardando solicitud personalizada desde /api/solicitudes/personalizada");
            System.out.println("🔎 Usuario ID: " + userId + ", Producto sugerido: " + dto.getNombreProductoSugerido());

            SolicitudPersonalizada guardada = solicitudService.save(nueva);
            return ResponseEntity.status(HttpStatus.CREATED).body(SolicitudPersonalizadaDto.from(guardada));
        } catch (Exception e) {
            System.err.println("❌ Error al guardar solicitud personalizada:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Solicitud no guardada"));
        }
    }

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ROLE_EMPLEADO')")
    public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudPersonalizadaDto dto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            System.out.println("📨 Petición recibida para crear solicitud personalizada");
            System.out.println("🔎 DTO recibido: nombre = " + dto.getNombreProductoSugerido() +
                    ", cantidad = " + dto.getCantidadDeseada() +
                    ", motivo = " + dto.getDescripcion());

            Long userId = userDetails.getId();
            System.out.println("🔐 ID del usuario autenticado: " + userId);
            User usuario = userService.findById(userId);

            if (usuario == null) {
                System.err.println("❌ Usuario autenticado no encontrado.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no válido.");
            }

            solicitudService.crearSolicitud(dto, usuario);
            System.out.println("✅ Solicitud personalizada procesada correctamente.");
            return ResponseEntity.ok(Map.of("mensaje", "Solicitud personalizada enviada correctamente."));
        } catch (Exception e) {
            System.err.println("❌ Error al procesar solicitud personalizada:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error interno al crear la solicitud personalizada"));
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody SolicitudPersonalizadaDto dto) {
        try {
            System.out.println("🔁 Cambiando estado de solicitud personalizada ID: " + id);
            SolicitudPersonalizada solicitud = dto.to();
            solicitud.setFechaResolucion(Instant.now());

            SolicitudPersonalizada actualizada = solicitudService.updateEstado(solicitud, id);
            if (actualizada != null) {
                return ResponseEntity.ok(SolicitudPersonalizadaDto.from(actualizada));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Solicitud no encontrada"));
        } catch (Exception e) {
            System.err.println("❌ Error al actualizar estado de solicitud personalizada:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cambiar estado de solicitud.");
        }
    }

    @PutMapping("/{id}/marcar-leida")
    @PreAuthorize("hasRole('ROLE_ADMIN_EMPRESA')")
    public ResponseEntity<?> marcarComoLeida(@PathVariable Long id) {
        try {
            System.out.println("🔄 Marcando solicitud personalizada como leída: ID " + id);
            SolicitudPersonalizada solicitud = solicitudService.findById(id);

            if (solicitud == null) {
                System.err.println("❌ Solicitud no encontrada.");
                return ResponseEntity.status(404).body(Map.of("error", "Solicitud no encontrada"));
            }

            solicitud.setLeida(true);
            solicitudService.save(solicitud);

            System.out.println("✅ Solicitud personalizada marcada como leída.");
            return ResponseEntity.ok(Map.of("mensaje", "Solicitud personalizada marcada como leída"));
        } catch (Exception e) {
            System.err.println("❌ Error al marcar como leída:");
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Error al actualizar estado de lectura"));
        }
    }

    @GetMapping("/noleidas")
    @PreAuthorize("hasRole('ROLE_ADMIN_EMPRESA')")
    public ResponseEntity<?> getSolicitudesNoLeidas(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            System.out.println("🔔 Buscando solicitudes personalizadas no leídas...");
            User admin = userService.findById(userDetails.getId());

            if (admin.getEmpresa() == null) {
                System.err.println("❌ El administrador no tiene empresa asociada.");
                return ResponseEntity.badRequest().body(Map.of("error", "El administrador no está asociado a ninguna empresa"));
            }

            List<SolicitudPersonalizada> solicitudes = solicitudService.findByEmpresaAndLeidaFalse(admin.getEmpresa());
            System.out.println("📬 Solicitudes no leídas encontradas: " + solicitudes.size());
            return ResponseEntity.ok(solicitudes.stream().map(SolicitudPersonalizadaDto::from).toList());
        } catch (Exception e) {
            System.err.println("❌ Error al obtener solicitudes no leídas:");
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Error interno al obtener notificaciones"));
        }
    }

    @GetMapping("/empresa")
    @PreAuthorize("hasRole('ROLE_ADMIN_EMPRESA')")
    public ResponseEntity<?> getSolicitudesPorEmpresa(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            System.out.println("🏢 Obteniendo solicitudes personalizadas para empresa del admin...");
            User admin = userService.findById(userDetails.getId());

            if (admin.getEmpresa() == null) {
                System.err.println("❌ El administrador no tiene empresa asignada");
                return ResponseEntity.badRequest().body(Map.of("error", "El administrador no está asociado a ninguna empresa"));
            }

            List<SolicitudPersonalizada> solicitudes = solicitudService.findByEmpresa(admin.getEmpresa());
            System.out.println("📊 Solicitudes encontradas: " + solicitudes.size());
            return ResponseEntity.ok(solicitudes.stream().map(SolicitudPersonalizadaDto::from).toList());
        } catch (Exception e) {
            System.err.println("❌ Error al obtener solicitudes por empresa:");
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Error interno al obtener solicitudes"));
        }
    }
}
