package com.tfg.backend.controller;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.services.UserDetailsImpl;
import com.tfg.backend.model.dto.SolicitudMovimientoDto;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.entity.SolicitudMovimiento;
import com.tfg.backend.model.enums.EstadoSolicitud;
import com.tfg.backend.model.repository.SolicitudMovimientoRepository;
import com.tfg.backend.service.SolicitudMovimientoService;
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
@RequestMapping("/api/solicitudes/movimiento")
@Slf4j
public class SolicitudMovimientoController {

    @Autowired
    private SolicitudMovimientoService solicitudService;

    @Autowired
    private SolicitudMovimientoRepository solicitudMovimientoRepository;

    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<SolicitudMovimientoDto> listAll() {
        return SolicitudMovimientoDto.from(solicitudService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        SolicitudMovimiento solicitud = solicitudService.findById(id);
        if (solicitud != null) {
            return ResponseEntity.ok(SolicitudMovimientoDto.from(solicitud));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Solicitud no encontrada"));
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<SolicitudMovimientoDto> findByUsuario(@PathVariable Long usuarioId) {
        return SolicitudMovimientoDto.from(solicitudService.findByUsuarioId(usuarioId));
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody SolicitudMovimientoDto dto,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            Long userId = userDetails.getId();
            dto.setUsuarioId(userId);
            SolicitudMovimiento nueva = dto.to();
            nueva.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoSolicitud.PENDIENTE);

            System.out.println("📝 Guardando solicitud desde endpoint /api/solicitudes/movimiento");
            System.out.println("🔎 Usuario ID: " + userId + ", Producto ID: " + dto.getProductoId());

            SolicitudMovimiento guardada = solicitudService.save(nueva);
            return ResponseEntity.status(HttpStatus.CREATED).body(SolicitudMovimientoDto.from(guardada));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Solicitud no guardada"));
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody SolicitudMovimientoDto dto) {
        try {
            System.out.println("🔁 Cambiando estado de solicitud ID: " + id);
            SolicitudMovimiento solicitud = dto.to();
            solicitud.setFechaResolucion(Instant.now());

            SolicitudMovimiento actualizada = solicitudService.updateEstado(solicitud, id);
            if (actualizada != null) {
                return ResponseEntity.ok(SolicitudMovimientoDto.from(actualizada));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Solicitud no encontrada"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cambiar estado de solicitud.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            System.out.println("🗑 Eliminando solicitud ID: " + id);
            SolicitudMovimiento solicitud = solicitudService.findById(id);
            if (solicitud != null) {
                solicitudService.delete(id);
                return ResponseEntity.ok("Solicitud eliminada correctamente.");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Solicitud no encontrada"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar solicitud.");
        }
    }

    @PostMapping("/crear")
    @PreAuthorize("hasRole('ROLE_EMPLEADO')")
    public ResponseEntity<?> crearSolicitud(@RequestBody SolicitudMovimientoDto dto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            System.out.println("📨 Petición recibida para crear solicitud de movimiento");
            System.out.println("🔎 DTO recibido: productoId = " + dto.getProductoId() +
                    ", cantidad = " + dto.getCantidadSolicitada() +
                    ", motivo = " + dto.getMotivo());

            Long userId = userDetails.getId();
            User usuario = userService.findById(userId);

            if (usuario == null) {
                System.err.println("❌ No se encontró el usuario autenticado.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no válido.");
            }

            solicitudService.crearSolicitud(dto, usuario);
            return ResponseEntity.ok(Map.of("mensaje", "Solicitud enviada correctamente."));
        } catch (Exception e) {
            System.err.println("❌ Error al procesar la solicitud de movimiento:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }

    @GetMapping("/noleidas")
    @PreAuthorize("hasRole('ROLE_ADMIN_EMPRESA')")
    public ResponseEntity<?> getSolicitudesNoLeidas(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User admin = userService.findById(userDetails.getId());
            if (admin.getEmpresa() == null) {
                System.err.println("⚠️ Admin sin empresa asignada.");
                return ResponseEntity.badRequest().body(Map.of("error", "El administrador no está asociado a ninguna empresa"));
            }

            List<SolicitudMovimiento> solicitudes = solicitudMovimientoRepository
                    .findByUsuario_EmpresaAndLeidaFalse(admin.getEmpresa());

            System.out.println("📬 Solicitudes no leídas encontradas: " + solicitudes.size());
            return ResponseEntity.ok(solicitudes.stream().map(SolicitudMovimientoDto::from).toList());
        } catch (Exception e) {
            System.err.println("❌ Error al obtener solicitudes no leídas:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener notificaciones.");
        }
    }

    @PutMapping("/{id}/marcar-leida")
    @PreAuthorize("hasRole('ROLE_ADMIN_EMPRESA')")
    public ResponseEntity<?> marcarComoLeida(@PathVariable Long id) {
        try {
            SolicitudMovimiento solicitud = solicitudMovimientoRepository.findById(id).orElse(null);
            if (solicitud == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Solicitud no encontrada"));
            }

            solicitud.setLeida(true);
            solicitudMovimientoRepository.save(solicitud);

            System.out.println("✅ Solicitud ID " + id + " marcada como leída.");
            return ResponseEntity.ok(Map.of("mensaje", "Solicitud marcada como leída"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al marcar como leída.");
        }
    }

}
