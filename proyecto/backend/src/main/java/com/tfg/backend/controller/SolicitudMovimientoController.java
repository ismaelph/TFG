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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/solicitudes/movimiento")
@Slf4j
public class SolicitudMovimientoController {

    @Autowired
    private SolicitudMovimientoService solicitudService;

    @Autowired
    private SolicitudMovimientoService solicitudMovimientoService;

    @Autowired
    private UserService userService;

    // GET – Listar todas
    @GetMapping("")
    public List<SolicitudMovimientoDto> listAll() {
        return SolicitudMovimientoDto.from(solicitudService.findAll());
    }

    // GET – Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        SolicitudMovimiento solicitud = solicitudService.findById(id);
        if (solicitud != null) {
            return ResponseEntity.ok(SolicitudMovimientoDto.from(solicitud));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.from("Solicitud no encontrada"));
    }

    // GET – Buscar por usuario
    @GetMapping("/usuario/{usuarioId}")
    public List<SolicitudMovimientoDto> findByUsuario(@PathVariable Long usuarioId) {
        return SolicitudMovimientoDto.from(solicitudService.findByUsuarioId(usuarioId));
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody SolicitudMovimientoDto dto,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            // 🔐 Asignar automáticamente el usuario autenticado
            Long userId = userDetails.getId();
            dto.setUsuarioId(userId);

            SolicitudMovimiento nueva = dto.to();
            nueva.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoSolicitud.PENDIENTE);

            SolicitudMovimiento guardada = solicitudService.save(nueva);
            return ResponseEntity.status(HttpStatus.CREATED).body(SolicitudMovimientoDto.from(guardada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Solicitud no guardada"));
        }
    }


    // PUT – Cambiar estado (aprobar/rechazar)
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody SolicitudMovimientoDto dto) {
        SolicitudMovimiento solicitud = dto.to();
        solicitud.setFechaResolucion(Instant.now());

        SolicitudMovimiento actualizada = solicitudService.updateEstado(solicitud, id);
        if (actualizada != null) {
            return ResponseEntity.ok(SolicitudMovimientoDto.from(actualizada));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Solicitud no encontrada"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        SolicitudMovimiento solicitud = solicitudService.findById(id);
        if (solicitud != null) {
            solicitudService.delete(id);
            return ResponseEntity.ok("Solicitud eliminada correctamente.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorDto.from("Solicitud no encontrada"));
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

            // Obtener usuario autenticado desde userDetails
            Long userId = userDetails.getId();
            System.out.println("🔐 ID del usuario autenticado: " + userId);

            User usuario = userService.findById(userId);
            if (usuario == null) {
                System.err.println("❌ No se encontró el usuario autenticado.");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no válido.");
            }

            // Llamar al servicio con el DTO y el usuario
            solicitudMovimientoService.crearSolicitud(dto, usuario);
            System.out.println("✅ Solicitud procesada correctamente.");

            return ResponseEntity.ok("Solicitud enviada correctamente.");
        } catch (Exception e) {
            System.err.println("❌ Error al procesar la solicitud de movimiento:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor.");
        }
    }


}
