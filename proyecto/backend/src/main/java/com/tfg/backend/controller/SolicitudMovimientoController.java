package com.tfg.backend.controller;

import com.tfg.backend.auth.services.UserDetailsImpl;
import com.tfg.backend.model.dto.SolicitudMovimientoDto;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.entity.SolicitudMovimiento;
import com.tfg.backend.model.enums.EstadoSolicitud;
import com.tfg.backend.service.SolicitudMovimientoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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

}
