package com.tfg.backend.model.dto;

import com.tfg.backend.auth.models.User;
import com.tfg.backend.model.entity.SolicitudPersonalizada;
import com.tfg.backend.model.enums.EstadoSolicitud;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class SolicitudPersonalizadaDto {

    private Long id;
    private String nombreProductoSugerido;
    private String descripcion;
    private Integer cantidadDeseada;
    private Long usuarioId;
    private EstadoSolicitud estado;
    private String respuestaAdmin;
    private Instant fechaSolicitud;
    private Instant fechaResolucion;

    public static SolicitudPersonalizadaDto from(SolicitudPersonalizada entity) {
        SolicitudPersonalizadaDto dto = new SolicitudPersonalizadaDto();

        if (entity == null) return dto;

        dto.setId(entity.getId());

        if (entity.getNombreProductoSugerido() != null) {
            dto.setNombreProductoSugerido(entity.getNombreProductoSugerido());
        }

        if (entity.getDescripcion() != null) {
            dto.setDescripcion(entity.getDescripcion());
        }

        if (entity.getCantidadDeseada() != null) {
            dto.setCantidadDeseada(entity.getCantidadDeseada());
        }

        if (entity.getUsuario() != null && entity.getUsuario().getId() != null) {
            dto.setUsuarioId(entity.getUsuario().getId());
        }

        if (entity.getEstado() != null) {
            dto.setEstado(entity.getEstado());
        }

        if (entity.getRespuestaAdmin() != null) {
            dto.setRespuestaAdmin(entity.getRespuestaAdmin());
        }

        if (entity.getFechaSolicitud() != null) {
            dto.setFechaSolicitud(entity.getFechaSolicitud());
        }

        if (entity.getFechaResolucion() != null) {
            dto.setFechaResolucion(entity.getFechaResolucion());
        }

        // Logs y consola
        System.out.println("🟢 DTO generado desde entidad: ID = " + entity.getId());
        if (log != null) {
            log.info("DTO generado desde entidad: ID = {}", entity.getId());
            log.info("Usuario asociado: {}", dto.getUsuarioId());
        }

        return dto;
    }

    public SolicitudPersonalizada to() {
        SolicitudPersonalizada entity = new SolicitudPersonalizada();

        entity.setId(this.getId());

        if (this.nombreProductoSugerido != null) {
            entity.setNombreProductoSugerido(this.nombreProductoSugerido);
        }

        if (this.descripcion != null) {
            entity.setDescripcion(this.descripcion);
        }

        if (this.cantidadDeseada != null) {
            entity.setCantidadDeseada(this.cantidadDeseada);
        }

        if (this.estado != null) {
            entity.setEstado(this.estado);
        }

        if (this.respuestaAdmin != null) {
            entity.setRespuestaAdmin(this.respuestaAdmin);
        }

        if (this.fechaSolicitud != null) {
            entity.setFechaSolicitud(this.fechaSolicitud);
        }

        if (this.fechaResolucion != null) {
            entity.setFechaResolucion(this.fechaResolucion);
        }

        if (this.usuarioId != null) {
            User user = new User();
            user.setId(this.usuarioId);
            entity.setUsuario(user);
        }

        // Logs y consola
        System.out.println("🛠️ Entidad generada desde DTO: ID = " + this.getId());
        if (log != null) {
            log.info("Entidad generada desde DTO: ID = {}", this.getId());
            log.info("Usuario ID asignado: {}", this.usuarioId);
        }

        return entity;
    }

    public static List<SolicitudPersonalizadaDto> from(List<SolicitudPersonalizada> list) {
        List<SolicitudPersonalizadaDto> dtos = new ArrayList<>();
        if (list != null) {
            for (SolicitudPersonalizada entity : list) {
                dtos.add(from(entity));
            }
        }
        return dtos;
    }
}
