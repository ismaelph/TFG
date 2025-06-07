package com.tfg.backend.controller;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.auth.services.UserDetailsImpl;
import com.tfg.backend.model.dto.ErrorDto;
import com.tfg.backend.model.dto.SolicitudMovimientoDto;
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

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/solicitudes/movimiento")
@Slf4j
public class SolicitudMovimientoController {

    @Autowired private SolicitudMovimientoService solicitudService;
    @Autowired private SolicitudMovimientoRepository solicitudMovimientoRepository;
    @Autowired private UserService userService;

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

    @GetMapping("/empresa")
    @PreAuthorize("hasRole('ROLE_ADMIN_EMPRESA')")
    public ResponseEntity<?> getSolicitudesDeMiEmpresa(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            User admin = userService.findById(userDetails.getId());

            if (admin.getEmpresa() == null) {
                System.err.println("⚠️ Admin sin empresa asignada.");
                return ResponseEntity.badRequest().body(Map.of("error", "El administrador no está asociado a ninguna empresa"));
            }

            List<SolicitudMovimiento> solicitudes = solicitudService.findByEmpresa(admin.getEmpresa());
            System.out.println("📦 Solicitudes de empresa cargadas: " + solicitudes.size());

            return ResponseEntity.ok(solicitudes.stream().map(SolicitudMovimientoDto::from).toList());
        } catch (Exception e) {
            System.err.println("❌ Error al obtener solicitudes de empresa:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener solicitudes de empresa");
        }
    }

    @PutMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobarSolicitud(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        System.out.println("📡 Endpoint: PUT /api/solicitudes/movimiento/" + id + "/aprobar");
        System.out.println("🔐 Usuario autenticado: ID=" + userDetails.getId() + ", Username=" + userDetails.getUsername());

        try {
            // Lectura de datos de la petición
            boolean aceptar = (boolean) body.getOrDefault("aceptar", true);
            String respuestaAdmin = (String) body.getOrDefault("respuestaAdmin", "");

            System.out.println("📥 Petición recibida para aprobar solicitud ID: " + id);
            System.out.println("✅ Aceptar: " + aceptar);
            System.out.println("📝 Respuesta del admin: " + respuestaAdmin);

            // Llamada al servicio
            SolicitudMovimiento solicitud = solicitudService.resolverSolicitud(id, aceptar, respuestaAdmin);

            System.out.println("✅ Solicitud procesada correctamente. Estado final: " + solicitud.getEstado());
            return ResponseEntity.ok(SolicitudMovimientoDto.from(solicitud));

        } catch (Exception e) {
            // LOGS detallados del error
            System.err.println("❌ Error en el controlador al aprobar la solicitud:");
            System.err.println("🧨 Mensaje de error: " + e.getMessage());
            System.err.println("🧨 Localized: " + e.getLocalizedMessage());
            System.err.println("🧨 Clase de error: " + e.getClass().getName());
            System.err.println("🧨 Stack trace:");
            e.printStackTrace();

            // Devolución segura con detalles del error para frontend
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "error", "Error al aprobar la solicitud",
                            "message", e.getMessage(),
                            "exception", e.getClass().getSimpleName()
                    ));
        }
    }

    @GetMapping("/usuario/{id}/noleidas")
    @PreAuthorize("hasRole('ROLE_EMPLEADO')")
    public ResponseEntity<?> getNotificacionesMovimientoEmpleado(@PathVariable Long id) {
        try {
            System.out.println("🔍 [INICIO] Buscando solicitudes de movimiento NO leídas para el usuario ID: " + id);

            List<SolicitudMovimiento> todas = solicitudService.findByUsuarioId(id);
            System.out.println("📦 Total de solicitudes encontradas para usuario ID " + id + ": " + todas.size());

            todas.forEach(s -> {
                System.out.println("➡️ Solicitud ID: " + s.getId()
                        + " | Estado: " + s.getEstado()
                        + " | Leída empleado: " + s.isLeidaEmpleado()
                        + " | Producto: " + (s.getProducto() != null ? s.getProducto().getNombre() : "null"));
            });

            List<SolicitudMovimiento> filtradas = todas.stream()
                    .filter(s ->
                            (s.getEstado() == EstadoSolicitud.ENVIADA || s.getEstado() == EstadoSolicitud.RECHAZADA)
                                    && !s.isLeidaEmpleado()
                    )
                    .toList();

            System.out.println("✅ Total solicitudes que cumplen filtro (ENVIADA o RECHAZADA y no leída por empleado): " + filtradas.size());

            List<SolicitudMovimientoDto> resultado = filtradas.stream()
                    .map(SolicitudMovimientoDto::from)
                    .toList();

            resultado.forEach(dto -> {
                System.out.println("📤 DTO generado -> ID: " + dto.getId()
                        + " | Estado: " + dto.getEstado()
                        + " | Producto: " + dto.getProductoNombre()
                        + " | Respuesta admin: " + dto.getRespuestaAdmin());
            });

            System.out.println("🎯 [FIN] Retornando " + resultado.size() + " solicitudes al frontend.");
            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            System.err.println("❌ Error al obtener solicitudes de movimiento no leídas:");
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Error al cargar notificaciones de movimiento"));
        }
    }


    @PutMapping("/{id}/leida-empleado")
    @PreAuthorize("hasRole('ROLE_EMPLEADO')")
    public ResponseEntity<?> marcarComoLeidaPorEmpleado(@PathVariable Long id) {
        try {
            SolicitudMovimiento solicitud = solicitudService.findById(id);
            if (solicitud == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Solicitud no encontrada"));
            }

            solicitud.setLeidaEmpleado(true);
            solicitudService.save(solicitud);

            return ResponseEntity.ok(Map.of("mensaje", "Solicitud marcada como leída por el empleado"));
        } catch (Exception e) {
            System.err.println("❌ Error al marcar solicitud como leída por el empleado:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al marcar como leída por el empleado"));
        }
    }




}
