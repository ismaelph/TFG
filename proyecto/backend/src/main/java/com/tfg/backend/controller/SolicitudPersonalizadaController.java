package com.tfg.backend.controller;

import com.tfg.backend.auth.services.UserDetailsImpl;
import com.tfg.backend.model.dto.SolicitudPersonalizadaDto;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.entity.SolicitudPersonalizada;
import com.tfg.backend.model.enums.EstadoSolicitud;
import com.tfg.backend.service.SolicitudPersonalizadaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/solicitudes/personalizada")
@Slf4j
public class SolicitudPersonalizadaController {

    @Autowired
    private SolicitudPersonalizadaService solicitudService;

    @GetMapping("")
    public List<SolicitudPersonalizadaDto> listAll() {
        return SolicitudPersonalizadaDto.from(solicitudService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        SolicitudPersonalizada solicitud = solicitudService.findById(id);
        if (solicitud != null) {
            return ResponseEntity.ok(SolicitudPersonalizadaDto.from(solicitud));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Solicitud no encontrada"));
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<SolicitudPersonalizadaDto> findByUsuario(@PathVariable Long usuarioId) {
        return SolicitudPersonalizadaDto.from(solicitudService.findByUsuarioId(usuarioId));
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody SolicitudPersonalizadaDto dto,
                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            // 🔐 Asignar automáticamente el usuario autenticado
            Long userId = userDetails.getId();
            dto.setUsuarioId(userId);

            SolicitudPersonalizada nueva = dto.to();
            nueva.setEstado(dto.getEstado() != null ? dto.getEstado() : EstadoSolicitud.PENDIENTE);

            SolicitudPersonalizada guardada = solicitudService.save(nueva);
            return ResponseEntity.status(HttpStatus.CREATED).body(SolicitudPersonalizadaDto.from(guardada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorDto.from("Solicitud no guardada"));
        }
    }


    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable Long id, @RequestBody SolicitudPersonalizadaDto dto) {
        SolicitudPersonalizada solicitud = dto.to();
        solicitud.setFechaResolucion(Instant.now());

        SolicitudPersonalizada actualizada = solicitudService.updateEstado(solicitud, id);
        if (actualizada != null) {
            return ResponseEntity.ok(SolicitudPersonalizadaDto.from(actualizada));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Solicitud no encontrada"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        SolicitudPersonalizada solicitud = solicitudService.findById(id);
        if (solicitud != null) {
            solicitudService.delete(id);
            return ResponseEntity.ok("Solicitud eliminada correctamente.");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorDto.from("Solicitud no encontrada"));
    }
}
